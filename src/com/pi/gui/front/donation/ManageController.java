package com.pi.gui.front.donation;


import com.pi.entities.Donation;
import com.pi.gui.front.MainWindowController;
import com.pi.services.DonationService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topText.setText("Ajouter donation");
        btnAjout.setText("Ajouter");
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Donation donation = new Donation(
                    com.pi.gui.front.project.ShowAllController.currentProject,
                    Float.parseFloat(goalAmountTF.getText()),
                    Float.parseFloat(recivedAmountTF.getText()),
                    createdAtDP.getValue(),
                    updatedAtDP.getValue()
            );

            if (DonationService.getInstance().add(donation)) {
                AlertUtils.makeSuccessNotification("Donation ajouté avec succés");
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_DONATION);
            } else {
                AlertUtils.makeError("Error");
            }
        } else {
            AlertUtils.makeError("Error");
        }
    }


    private boolean controleDeSaisie() {

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