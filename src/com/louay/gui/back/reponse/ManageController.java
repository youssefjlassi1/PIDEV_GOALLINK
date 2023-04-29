package com.louay.gui.back.reponse;


import com.louay.entities.Reponse;
import com.louay.gui.back.MainWindowController;
import com.louay.services.ReponseService;
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

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<RelationObject> feedbackCB;
    @FXML
    public TextField texteTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Reponse currentReponse;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (RelationObject feedback : ReponseService.getInstance().getAllFeedbacks()) {
            feedbackCB.getItems().add(feedback);
        }

        currentReponse = ShowAllController.currentReponse;

        if (currentReponse != null) {
            topText.setText("Modifier reponse");
            btnAjout.setText("Modifier");

            try {
                feedbackCB.setValue(currentReponse.getFeedbackId());
                texteTF.setText(currentReponse.getTexte());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter reponse");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Reponse reponse = new Reponse(
                    feedbackCB.getValue(),
                    texteTF.getText()
            );

            if (currentReponse == null) {
                if (ReponseService.getInstance().add(reponse)) {
                    AlertUtils.makeSuccessNotification("Reponse ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_REPONSE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            } else {
                reponse.setId(currentReponse.getId());
                if (ReponseService.getInstance().edit(reponse)) {
                    AlertUtils.makeSuccessNotification("Reponse modifié avec succés");
                    ShowAllController.currentReponse = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_REPONSE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (feedbackCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir feedback");
            return false;
        }


        if (texteTF.getText().isEmpty()) {
            AlertUtils.makeInformation("texte ne doit pas etre vide");
            return false;
        }


        return true;
    }
}