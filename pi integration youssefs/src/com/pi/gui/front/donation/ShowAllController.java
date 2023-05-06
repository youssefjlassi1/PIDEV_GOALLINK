package com.pi.gui.front.donation;

import com.pi.entities.Donation;
import com.pi.entities.Project;

import com.pi.entities.Donation;
import com.pi.entities.DonationUser;
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
import javafx.scene.control.TextInputDialog;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javafx.scene.control.TextInputDialog;

public class ShowAllController implements Initializable {

    public static Donation currentDonation;

    @FXML
    public Text topText;
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

            ((Button) innerContainer.lookup("#donateButton")).setOnAction((event) -> donate(donation));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void donate(Donation donation) {
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
                chargeParams.put("description", "Donation for " + donation.getProject().getName()
                );
                Charge.create(chargeParams);
                DonationService.getInstance().save(new Donation(amount, donation));

                AlertUtils.makeInfo("Thank you for your donation!");
            } catch (NumberFormatException e) {
                AlertUtils.makeError("Invalid amount!");
            } catch (StripeException e) {
                AlertUtils.makeError("Error processing donation!");
            }
        });
    }

    @FXML
    public void sort(ActionEvent ignored) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listDonation);
        displayData();
    }

}
