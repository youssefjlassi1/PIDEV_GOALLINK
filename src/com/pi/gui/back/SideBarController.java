package com.pi.gui.back;

import com.pi.MainApp;
import com.pi.utils.Animations;
import com.pi.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#071e31");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnProjects;
    @FXML
    private Button btnDonations;
    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnProjects,
                btnDonations,
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnProjects.setTextFill(Color.WHITE);
        btnDonations.setTextFill(Color.WHITE);

    }

    @FXML
    private void afficherProjects(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_PROJECT);

        btnProjects.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnProjects, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherDonations(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_DONATION);

        btnDonations.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnDonations, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent ignored) {
        MainApp.getInstance().logout();
    }
}
