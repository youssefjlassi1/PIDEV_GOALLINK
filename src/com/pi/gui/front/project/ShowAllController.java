package com.pi.gui.front.project;

import com.pi.entities.Donation;
import com.pi.entities.Project;
import com.pi.gui.front.MainWindowController;
import com.pi.services.DonationService;
import com.pi.services.ProjectService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javafx.scene.control.TextInputDialog;



public class ShowAllController implements Initializable {

    public static Project currentProject;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;

    List<Project> listProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listProject = ProjectService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listProject);

        if (!listProject.isEmpty()) {
            for (Project project : listProject) {
                if (project.getName().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeProjectModel(project));
                }

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeProjectModel(
            Project project
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_PROJECT)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#ownerText")).setText("User : " + project.getOwner());
            ((Text) innerContainer.lookup("#nameText")).setText("Name : " + project.getName());
            ((Text) innerContainer.lookup("#themeText")).setText("Theme : " + project.getTheme());
            ((Text) innerContainer.lookup("#statusText")).setText("Status : " + project.getStatus());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + project.getDescription());
            ((Text) innerContainer.lookup("#locationText")).setText("Location : " + project.getLocation());
            ((Text) innerContainer.lookup("#createdText")).setText("Created : " + project.getCreated());
            ((Text) innerContainer.lookup("#updatedText")).setText("Updated : " + project.getUpdated());

            int totalAmount = 0;
            for (Donation donation : DonationService.getInstance().getAll()) {
                if (donation.getProject().getId() == project.getId()) {
                    totalAmount += donation.getRecivedAmount();
                }
            }

            ((Text) innerContainer.lookup("#donationsText")).setText("Donations amount : " + totalAmount);
            Path selectedImagePath = FileSystems.getDefault().getPath(project.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#donateButton")).setOnAction((event) -> donate(project));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterProject(ActionEvent ignored) {
        currentProject = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_PROJECT);
    }

    private void modifierProject(Project project) {
        currentProject = project;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_PROJECT);
    }

    private void supprimerProject(Project project) {
        currentProject = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer project ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (ProjectService.getInstance().delete(project.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_PROJECT);
                } else {
                    AlertUtils.makeError("Could not delete project");
                }
            }
        }
    }

  private void donate(Project project) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Donation");
    dialog.setHeaderText("Enter the amount you wish to donate (in cents):");
    Optional<String> result = dialog.showAndWait();

    result.ifPresent(amountStr -> {
        try {
            int amount = Integer.parseInt(amountStr);
            Stripe.apiKey = "sk_test_51Mhj2XD4qzKXKzK8h42qbguBFU4Rc7A72jEun0JGUOz9MA8wOibwc1bZYOeEX1BDZ2lugxyMq9VVQ7dWl9SjCQhe00yuNpst2P";
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "eur");
            chargeParams.put("source", "tok_visa");
            chargeParams.put("description", "Donation for " + project.getName());
            Charge.create(chargeParams);
         DonationService.getInstance().save(new Donation(amount, project));

            AlertUtils.makeInfo("Thank you for your donation!");
        } catch (NumberFormatException e) {
            AlertUtils.makeError("Invalid amount!");
        } catch (StripeException e) {
            AlertUtils.makeError("Error processing donation!");
        }
    });
}



    @FXML
    private void search(KeyEvent ignored) {
        displayData(searchTF.getText());
    }

    
    
    
}
