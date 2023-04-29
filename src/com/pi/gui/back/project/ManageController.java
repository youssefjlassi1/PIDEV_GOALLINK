package com.pi.gui.back.project;

import com.pi.MainApp;
import com.pi.entities.Project;
import com.pi.gui.back.MainWindowController;
import com.pi.services.ProjectService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import com.pi.utils.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<RelationObject> userCB;
    @FXML
    public TextField nameTF;
    @FXML
    public TextField themeTF;
    @FXML
    public TextField statusTF;
    @FXML
    public ImageView imageIV;
    @FXML
    public TextField descriptionTF;
    @FXML
    public TextField locationTF;
    @FXML
    public DatePicker createdDP;
    @FXML
    public DatePicker updatedDP;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Project currentProject;
    Path selectedImagePath;
    boolean imageEdited;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (RelationObject user : ProjectService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }

        currentProject = ShowAllController.currentProject;

        if (currentProject != null) {
            topText.setText("Modifier project");
            btnAjout.setText("Modifier");

            try {
                userCB.setValue(currentProject.getOwner());
                nameTF.setText(currentProject.getName());
                themeTF.setText(currentProject.getTheme());
                statusTF.setText(currentProject.getStatus());
                selectedImagePath = FileSystems.getDefault().getPath(currentProject.getImage());
                if (selectedImagePath.toFile().exists()) {
                    imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
                }
                descriptionTF.setText(currentProject.getDescription());
                locationTF.setText(currentProject.getLocation());
                createdDP.setValue(currentProject.getCreated());
                updatedDP.setValue(currentProject.getUpdated());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter project");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            String imagePath;
            if (imageEdited) {
                imagePath = currentProject.getImage();
            } else {
                createImageFile();
                imagePath = selectedImagePath.toString();
            }

            Project project = new Project(
                    userCB.getValue(),
                    nameTF.getText(),
                    themeTF.getText(),
                    statusTF.getText(),
                    imagePath,
                    descriptionTF.getText(),
                    locationTF.getText(),
                    createdDP.getValue(),
                    updatedDP.getValue()
            );

            if (currentProject == null) {
                if (ProjectService.getInstance().add(project)) {

                    try {
                        sendMail(project.getOwner().getName());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    AlertUtils.makeSuccessNotification("Project ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_PROJECT);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                project.setId(currentProject.getId());
                if (ProjectService.getInstance().edit(project)) {
                    AlertUtils.makeSuccessNotification("Project modifié avec succés");
                    ShowAllController.currentProject = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_PROJECT);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

            if (selectedImagePath != null) {
                createImageFile();
            }
        }
    }

    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "pidev.app.esprit@gmail.com";
        //Your gmail password
        String password = "jkemsuddgeptmcsb";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        if (message != null){
            Transport.send(message);
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Error sending the mail");
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Welcome");
            String htmlCode = "<h1>Notification</h1> <br/> <h2><b>Welcome to our application</b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @FXML
    public void chooseImage(ActionEvent ignored) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApp.mainStage);
        if (file != null) {
            selectedImagePath = Paths.get(file.getPath());
            imageIV.setImage(new Image(file.toURI().toString()));
        }
    }

    public void createImageFile() {
        try {
            Path newPath = FileSystems.getDefault().getPath("src/com/pi/images/uploads/" + selectedImagePath.getFileName());
            Files.copy(selectedImagePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            selectedImagePath = newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean controleDeSaisie() {


        if (userCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir user");
            return false;
        }


        if (nameTF.getText().isEmpty()) {
            AlertUtils.makeInformation("name ne doit pas etre vide");
            return false;
        }


        if (themeTF.getText().isEmpty()) {
            AlertUtils.makeInformation("theme ne doit pas etre vide");
            return false;
        }


        if (statusTF.getText().isEmpty()) {
            AlertUtils.makeInformation("status ne doit pas etre vide");
            return false;
        }


        if (selectedImagePath == null) {
            AlertUtils.makeInformation("Veuillez choisir une image");
            return false;
        }


        if (descriptionTF.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }


        if (locationTF.getText().isEmpty()) {
            AlertUtils.makeInformation("location ne doit pas etre vide");
            return false;
        }


        if (createdDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour created");
            return false;
        }


        if (updatedDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour updated");
            return false;
        }


        return true;
    }
}