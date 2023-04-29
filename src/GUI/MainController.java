/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gamer
 */
public class MainController implements Initializable {

    @FXML
    private Button TournamentList;
    @FXML
    private Button TaskList;
    @FXML
    private Button Calendar;
    @FXML
    private Text text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @FXML
    private void handleTournamentButton() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectList.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println("Error loading add tournament form: " + ex.getMessage());
    }
}
    @FXML
     private void handleMatchtButton() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskList.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println("Error loading add tournament form: " + ex.getMessage());
    }
}
     @FXML
     private void handleCalendarButton() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println("Error loading add tournament form: " + ex.getMessage());
    }
}
     private void handleLeagueButton() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TopPlayers.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println("Error loading add tournament form: " + ex.getMessage());
    }
}
     private void handleHomeButton() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TournamentsHomePage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println("Error loading add tournament form: " + ex.getMessage());
    }
}
}
