package com.pi.gui;

import com.pi.MainApp;
import com.pi.entities.User;
import com.pi.services.DonationUserService;
import com.pi.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public ComboBox<User> userCB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (User user : DonationUserService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }
    }


    @FXML
    public void frontend(ActionEvent ignored) {
        if (userCB.getValue() != null) {
            MainApp.session = userCB.getValue();
            MainApp.getInstance().loadFront();
        } else {
            AlertUtils.makeError("User null");
        }
    }

    @FXML
    public void backend(ActionEvent ignored) {
        if (userCB.getValue() != null) {
            MainApp.session = userCB.getValue();
            MainApp.getInstance().loadBack();
        } else {
            AlertUtils.makeError("User null");
        }
    }
}
