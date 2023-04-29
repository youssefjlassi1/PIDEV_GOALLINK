package com.pi.gui.front.donationUser;

import com.pi.entities.DonationUser;
import com.pi.services.DonationUserService;
import com.pi.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowAllController implements Initializable {

    public static DonationUser currentDonationUser;

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;

    List<DonationUser> listDonationUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listDonationUser = DonationUserService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listDonationUser);

        if (!listDonationUser.isEmpty()) {
            for (DonationUser donationUser : listDonationUser) {
                if (donationUser.getUser().toString().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeDonationUserModel(donationUser));
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

    public Parent makeDonationUserModel(
            DonationUser donationUser
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_DONATION_USER)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#userText")).setText("User : " + donationUser.getUser());
            ((Text) innerContainer.lookup("#amountText")).setText("Amount : " + donationUser.getAmount());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + donationUser.getDate());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }


    @FXML
    private void search(KeyEvent ignored) {
        displayData(searchTF.getText());
    }

}
