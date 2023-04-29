package com.louay.gui.back;

import com.louay.MainApp;
import com.louay.utils.Animations;
import com.louay.utils.Constants;
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

    private final Color COLOR_GRAY = Color.web("#F27338");
    private final Color COLOR_PRIMARY = Color.web("#009688");
    private final Color COLOR_DARK = Color.web("#FFFFFF");
    private Button[] liens;

    @FXML
    private Button btnReponses;
    @FXML
    private Button btnFeedbacks;
   
    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnReponses,
                btnFeedbacks,
                
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnReponses.setTextFill(Color.WHITE);
        btnFeedbacks.setTextFill(Color.WHITE);

    }

    @FXML
    private void afficherReponses(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_REPONSE);

        btnReponses.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnReponses, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherFeedbacks(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_FEEDBACK);

        btnFeedbacks.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnFeedbacks, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }


    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        MainApp.getInstance().logout();
    }
}
