/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.louay.gui.front.feedback;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ShowAllController implements Initializable {

    @FXML
    private Text topText;
    @FXML
    private TextField searchTF;
    @FXML
    private Button addButton;
    @FXML
    private VBox mainVBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void search(KeyEvent event) {
    }

    @FXML
    private void ajouterFeedback(ActionEvent event) {
    }
    
}
