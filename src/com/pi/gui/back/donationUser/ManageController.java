package com.pi.gui.back.donationUser;


import com.pi.MainApp;
import com.pi.entities.DonationUser;
import com.pi.gui.back.MainWindowController;
import com.pi.services.DonationUserService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField amountTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    DonationUser currentDonationUser;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentDonationUser = ShowAllController.currentDonationUser;

        if (currentDonationUser != null) {
            topText.setText("Modifier donationUser");
            btnAjout.setText("Modifier");

            try {
                amountTF.setText(String.valueOf(currentDonationUser.getAmount()));
            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter donation user");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            DonationUser donationUser = new DonationUser(
                    MainApp.session,
                    Float.parseFloat(amountTF.getText()),
                    LocalDate.now()
            );

            if (currentDonationUser == null) {
                if (DonationUserService.getInstance().add(donationUser)) {
                    AlertUtils.makeSuccessNotification("DonationUser ajouté avec succés");
                    //MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_DONATION_USER);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                donationUser.setId(currentDonationUser.getId());
                if (DonationUserService.getInstance().edit(donationUser)) {
                    AlertUtils.makeSuccessNotification("DonationUser modifié avec succés");
                    ShowAllController.currentDonationUser = null;
                   // MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_DONATION_USER);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {

        if (amountTF.getText().isEmpty()) {
            AlertUtils.makeInformation("amount ne doit pas etre vide");
            return false;
        }


        try {
            Float.parseFloat(amountTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("amount doit etre un réel");
            return false;
        }


        return true;
    }
}