/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entites.Project;
import entites.Project;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceProject;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AddProjectController implements Initializable {

    @FXML
    private TextField Name;
    @FXML
    private TextArea Description;
  
    @FXML
    private ImageView Photo;
    
    
    private String photoPath;
    @FXML
    private TextField status;
    @FXML
    private TextField location;
    @FXML
    private TextField theme;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
private void selectPhoto(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        photoPath = selectedFile.getAbsolutePath();
        javafx.scene.image.Image image = new javafx.scene.image.Image(new File(photoPath).toURI().toString());
        Photo.setImage(image);
    }
}
@FXML
private void goBack(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectList.fxml"));
        Parent root = loader.load();
        ProjectListController controller = loader.getController();
        Stage stage = (Stage) Name.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading Project list: " + ex.getMessage());
    }
}

@FXML
private void Ajouter(ActionEvent event) {
    String name;
    String description;
    String theme;
   
    name = Name.getText();
    description = Description.getText();
    theme =this.theme.getText();
    String status = this.status.getText();
    String location = this.location.getText();
    
            
    
    

    // CT
    if (name.isEmpty() || !name.matches("^[a-zA-Z]+$")) {
        showAlert("Error", "Invalid name", "Name should only contain alphabetic characters");
        return;
    }

  
  
     if (description.isEmpty() || description.length()<7 ){
        showAlert("Error", "Description is required", "Please enter a Valid description for the Project");
        return;
    }


    

    // Create Project 
   // Project p =new Project(name,description,prise,startdate,enddate,photoPath);
    Project p = new Project(2, name, theme, status, description, location, photoPath);
    ServiceProject t= new ServiceProject();
    t.Ajouter2(p);
    
    // show alert when Project is added successfully
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText("Project added successfully!");
    alert.showAndWait();

    // go back to ProjectList.fxml
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectList.fxml"));
        Parent root = loader.load();
        ProjectListController controller = loader.getController();
        Stage stage = (Stage) Name.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading Project list: " + ex.getMessage());
    }
}

private void showAlert(String title, String header, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}


    
   

    
}
