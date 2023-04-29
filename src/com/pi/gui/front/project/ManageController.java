package com.pi.gui.front.project;

import com.pi.MainApp;
import com.pi.entities.Project;
import com.pi.gui.front.MainWindowController;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;

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
                    AlertUtils.makeSuccessNotification("Project ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_PROJECT);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                project.setId(currentProject.getId());
                if (ProjectService.getInstance().edit(project)) {
                    AlertUtils.makeSuccessNotification("Project modifié avec succés");
                    ShowAllController.currentProject = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_PROJECT);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

            if (selectedImagePath != null) {
                createImageFile();
            }
        }
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
    public class StripeExample {

    public void main(String[] args) {
        Stripe.apiKey = "sk_test_51Mhj2XD4qzKXKzK8h42qbguBFU4Rc7A72jEun0JGUOz9MA8wOibwc1bZYOeEX1BDZ2lugxyMq9VVQ7dWl9SjCQhe00yuNpst2P";

        CustomerCreateParams params =
            CustomerCreateParams
                .builder()
                .setDescription("Example description")
                .setEmail("test@example.com")
                .setPaymentMethod("pm_card_visa")  // obtained via Stripe.js
                .build();

        try {
            Customer customer = Customer.create(params);
            System.out.println(customer);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
    
    
}