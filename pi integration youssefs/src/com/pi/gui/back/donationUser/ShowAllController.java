package com.pi.gui.back.donationUser;

import com.pi.entities.DonationUser;
import com.pi.gui.back.MainWindowController;
import com.pi.services.DonationUserService;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static DonationUser currentDonationUser;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
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
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_DONATION_USER)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#userText")).setText("User : " + donationUser.getUser());
            ((Text) innerContainer.lookup("#amountText")).setText("Amount : " + donationUser.getAmount());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + donationUser.getDate());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierDonationUser(donationUser));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerDonationUser(donationUser));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterDonationUser(ActionEvent ignored) {
        currentDonationUser = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_DONATION_USER);
    }

    private void modifierDonationUser(DonationUser donationUser) {
        currentDonationUser = donationUser;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_DONATION_USER);
    }

    private void supprimerDonationUser(DonationUser donationUser) {
        currentDonationUser = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer donationUser ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (DonationUserService.getInstance().delete(donationUser.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_DONATION_USER);
                } else {
                    AlertUtils.makeError("Could not delete donationUser");
                }
            }
        }
    }


    @FXML
    private void search(KeyEvent ignored) {
        displayData(searchTF.getText());
    }

}
