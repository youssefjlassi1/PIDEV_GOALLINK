package com.pi.gui.front.donation;

import com.pi.entities.Donation;
import com.pi.gui.front.MainWindowController;
import com.pi.services.DonationService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Donation currentDonation;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;

    List<Donation> listDonation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listDonation = DonationService.getInstance().getAll();
        sortCB.getItems().addAll("Project", "GoalAmount", "RecivedAmount", "CreatedAt", "UpdatedAt");
        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listDonation);

        if (!listDonation.isEmpty()) {
            for (Donation donation : listDonation) {
                mainVBox.getChildren().add(makeDonationModel(donation));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeDonationModel(
            Donation donation
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_DONATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#projectText")).setText("Project : " + donation.getProject());
            ((Text) innerContainer.lookup("#goalAmountText")).setText("GoalAmount : " + donation.getGoalAmount());
            ((Text) innerContainer.lookup("#recivedAmountText")).setText("RecivedAmount : " + donation.getRecivedAmount());
            ((Text) innerContainer.lookup("#createdAtText")).setText("CreatedAt : " + donation.getCreatedAt());
            ((Text) innerContainer.lookup("#updatedAtText")).setText("UpdatedAt : " + donation.getUpdatedAt());


            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerDonation(donation));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterDonation(ActionEvent ignored) {
        currentDonation = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_DONATION);
    }

    private void supprimerDonation(Donation donation) {
        currentDonation = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer donation ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (DonationService.getInstance().delete(donation.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_DONATION);
                } else {
                    AlertUtils.makeError("Could not delete donation");
                }
            }
        }
    }


    @FXML
    public void sort(ActionEvent ignored) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listDonation);
        displayData();
    }

}
