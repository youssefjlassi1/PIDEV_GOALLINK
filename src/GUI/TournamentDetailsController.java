/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import entites.Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceProject;

/**
 * FXML Controller class
 *
 * @author elroug3
 */
public class TournamentDetailsController implements Initializable {

    @FXML
    private ImageView imgView;
    @FXML
    private Label titreLabel;
   
    @FXML
    private Text descLabel;
    
            private Project selectedT;
    ServiceProject prod = new ServiceProject();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

     public void show(Project tournament) throws FileNotFoundException {
        selectedT = tournament;
        titreLabel.setText(selectedT.getName());
        descLabel.setText(selectedT.getDescription());
        
             FileInputStream input = new FileInputStream(tournament.getImage());
                Image image = new Image(input);
            
            
            imgView.setImage(image);
    }

    
    
}
