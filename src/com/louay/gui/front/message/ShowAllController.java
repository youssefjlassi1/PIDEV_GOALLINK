package com.louay.gui.front.message;

import com.louay.MainApp;
import com.louay.entities.Message;
import com.louay.gui.front.MainWindowController;
import com.louay.services.MessageService;
import com.louay.utils.AlertUtils;
import com.louay.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Message currentMessage;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;


    List<Message> listMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listMessage = MessageService.getInstance().getAll();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listMessage);

        if (!listMessage.isEmpty()) {
            for (Message message : listMessage) {
                if (message.getSender().getId() == MainApp.session.getId() || message.getReceiver().getId() == MainApp.session.getId()) {
                    mainVBox.getChildren().add(makeMessageModel(message));
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

    public Parent makeMessageModel(
            Message message
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_MESSAGE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            if (message.getSender().getId() == MainApp.session.getId()) {
                ((Text) innerContainer.lookup("#senderText")).setText("Expediteur :\n Vous");
            } else {
                ((Text) innerContainer.lookup("#senderText")).setText("Expediteur :\n " + message.getSender());
            }

            if (message.getReceiver().getId() == MainApp.session.getId()) {
                ((Text) innerContainer.lookup("#receiverText")).setText("Destinataire :\n Vous");
            } else {
                ((Text) innerContainer.lookup("#receiverText")).setText("Destinataire :\n " + message.getReceiver());
            }

            ((Text) innerContainer.lookup("#messageText")).setText("Message :\n " + message.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterMessage(ActionEvent event) {
        currentMessage = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_MESSAGE);
    }
}
