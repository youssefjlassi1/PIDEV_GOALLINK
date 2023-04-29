package com.louay.gui.front.Request;


import com.louay.entities.Request;
import com.louay.gui.front.MainWindowController;
import com.louay.services.RequestService;
import com.louay.services.MessageService;
import com.louay.utils.AlertUtils;
import com.louay.utils.Constants;
import com.louay.utils.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<RelationObject> userCB;
    @FXML
    public ComboBox<String> sujetCB;
    @FXML
    public TextField emailTF;
    @FXML
    public TextField descriptionTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Request currentFeedback;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

           for (RelationObject user : MessageService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }

        sujetCB.getItems().add("Sponsoorship");
        sujetCB.getItems().add("Partnersip");
        sujetCB.getItems().add("Participation");



        currentFeedback = ShowAllController.currentFeedback;

        if (currentFeedback != null) {
            topText.setText("Modifier request");
            btnAjout.setText("Modifier");

            try {
                userCB.setValue(currentFeedback.getUserId());
                sujetCB.setValue(currentFeedback.getSujet());
                emailTF.setText(currentFeedback.getEmail());
                descriptionTF.setText(currentFeedback.getDescription());
            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter feedback");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Request feedback = new Request(
                    userCB.getValue(),
                    sujetCB.getValue(),
                    emailTF.getText(),
                    descriptionTF.getText(),
                    LocalDate.now(),
                    0
            );

            if (currentFeedback == null) {
                if (RequestService.getInstance().add(feedback)) {
                    try {
                        sendMail(emailTF.getText());
                    } catch (Exception e) {
                        System.out.println("Mail send failed");
                    }
                    AlertUtils.makeSuccessNotification("Feedback ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_FEEDBACK);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            } else {
                feedback.setId(currentFeedback.getId());
                feedback.setStatus(currentFeedback.getStatus());

                if (RequestService.getInstance().edit(feedback)) {
                    AlertUtils.makeSuccessNotification("Feedback modifié avec succés");
                    ShowAllController.currentFeedback = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_FEEDBACK);
                } else {
                    AlertUtils.makeError("Erreur");
                }
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
        if (message != null) {
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
            message.setSubject("Notification");
            String htmlCode = "<h1>Notification</h1> <br/> <h2><b>Feedback ajouté avec succes</b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean controleDeSaisie() {


        if (userCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir user");
            return false;
        }

        if (sujetCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir sujet");
            return false;
        }

        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformation("email ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(emailTF.getText()).matches()) {
            AlertUtils.makeInformation("Email invalide");
            return false;
        }

        if (descriptionTF.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }

        return true;
    }
}