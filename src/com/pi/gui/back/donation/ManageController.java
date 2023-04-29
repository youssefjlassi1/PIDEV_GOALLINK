package com.pi.gui.back.donation;


import com.pi.entities.Donation;
import com.pi.entities.Project;
import com.pi.gui.back.MainWindowController;
import com.pi.services.DonationService;
import com.pi.services.ProjectService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<Project> projectCB;
    @FXML
    public TextField goalAmountTF;
    @FXML
    public TextField recivedAmountTF;
    @FXML
    public DatePicker createdAtDP;
    @FXML
    public DatePicker updatedAtDP;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Donation currentDonation;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Project project : ProjectService.getInstance().getAll()) {
            projectCB.getItems().add(project);
        }

        currentDonation = ShowAllController.currentDonation;

        if (currentDonation != null) {
            topText.setText("Modifier donation");
            btnAjout.setText("Modifier");

            try {
                projectCB.setValue(currentDonation.getProject());
                goalAmountTF.setText(String.valueOf(currentDonation.getGoalAmount()));
                recivedAmountTF.setText(String.valueOf(currentDonation.getRecivedAmount()));
                createdAtDP.setValue(currentDonation.getCreatedAt());
                updatedAtDP.setValue(currentDonation.getUpdatedAt());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter donation");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Donation donation = new Donation(
                    projectCB.getValue(),
                    Float.parseFloat(goalAmountTF.getText()),
                    Float.parseFloat(recivedAmountTF.getText()),
                    createdAtDP.getValue(),
                    updatedAtDP.getValue()
            );

            if (currentDonation == null) {
                if (DonationService.getInstance().add(donation)) {
                    AlertUtils.makeSuccessNotification("Donation ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_DONATION);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                donation.setId(currentDonation.getId());
                if (DonationService.getInstance().edit(donation)) {
                    AlertUtils.makeSuccessNotification("Donation modifié avec succés");
                    ShowAllController.currentDonation = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_DONATION);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (projectCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir project");
            return false;
        }


        if (goalAmountTF.getText().isEmpty()) {
            AlertUtils.makeInformation("goalAmount ne doit pas etre vide");
            return false;
        }


        try {
            Float.parseFloat(goalAmountTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("goalAmount doit etre un réel");
            return false;
        }
        if (recivedAmountTF.getText().isEmpty()) {
            AlertUtils.makeInformation("recivedAmount ne doit pas etre vide");
            return false;
        }


        try {
            Float.parseFloat(recivedAmountTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("recivedAmount doit etre un réel");
            return false;
        }
        if (createdAtDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour createdAt");
            return false;
        }


        if (updatedAtDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour updatedAt");
            return false;
        }


        return true;
    }
}