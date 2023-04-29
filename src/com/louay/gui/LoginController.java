package com.louay.gui;

import com.louay.MainApp;
import com.louay.services.MessageService;
import com.louay.utils.AlertUtils;
import com.louay.utils.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public ComboBox<RelationObject> userCB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (RelationObject user : MessageService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }
    }


    @FXML
    public void frontend(ActionEvent actionEvent) {
        if (userCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir user");
        } else {
            MainApp.session = userCB.getValue();
            MainApp.getInstance().loadFront();
        }
    }

    @FXML
    public void backend(ActionEvent actionEvent) {
        MainApp.getInstance().loadBack();
    }
}
