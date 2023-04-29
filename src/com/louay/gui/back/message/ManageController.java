package com.louay.gui.back.message;


import com.louay.MainApp;
import com.louay.entities.Message;
import com.louay.gui.back.MainWindowController;
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
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField senderTF;
    @FXML
    public ComboBox<RelationObject> userCB;
    @FXML
    public TextField messageTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Message currentMessage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (RelationObject user : MessageService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }

        currentMessage = ShowAllController.currentMessage;

        if (currentMessage != null) {
            topText.setText("Modifier message");
            btnAjout.setText("Modifier");

            try {
                senderTF.setText(String.valueOf(currentMessage.getSender()));
                userCB.setValue(currentMessage.getReceiver());
                messageTF.setText(currentMessage.getMessage());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter message");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Message message = new Message(
                    MainApp.session,
                    userCB.getValue(),
                    messageTF.getText()
            );

            if (currentMessage == null) {
                if (MessageService.getInstance().add(message)) {
                    AlertUtils.makeInformation("Message ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_MESSAGE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            } else {
                message.setId(currentMessage.getId());
                if (MessageService.getInstance().edit(message)) {
                    AlertUtils.makeInformation("Message modifié avec succés");
                    ShowAllController.currentMessage = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_MESSAGE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (senderTF.getText().isEmpty()) {
            AlertUtils.makeInformation("sender ne doit pas etre vide");
            return false;
        }


        try {
            Integer.parseInt(senderTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("sender doit etre un nombre");
            return false;
        }

        if (userCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir user");
            return false;
        }


        if (messageTF.getText().isEmpty()) {
            AlertUtils.makeInformation("message ne doit pas etre vide");
            return false;
        }


        return true;
    }
}