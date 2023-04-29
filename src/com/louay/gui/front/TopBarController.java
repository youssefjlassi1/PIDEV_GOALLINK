package com.louay.gui.front;

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

public class TopBarController implements Initializable {

       private final Color COLOR_GRAY = Color.web("#F27338");
    private final Color COLOR_PRIMARY = Color.web("#009688");
    private final Color COLOR_DARK = Color.web("#FFFFFF");
    private Button[] liens;

    @FXML
    private Button btnFeedbacks;

    @FXML
    private Button btnMessages;

    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnFeedbacks,
              
                btnMessages,
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }

        btnFeedbacks.setTextFill(COLOR_DARK);

    }

    @FXML
    private void afficherFeedbacks(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_FEEDBACK);

        btnFeedbacks.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnFeedbacks, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherMessages(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_MESSAGE);

        btnMessages.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnMessages, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
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
