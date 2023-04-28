/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import entites.Task;
import entites.Project;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceTask;
import services.ServiceProject;

/**
 * FXML Controller class
 *
 * @author Gamer
 */
public class AddMatchController implements Initializable {
    
  
    
    
   
    private ServiceProject tournament1;
    @FXML
    private TextField Name;
    @FXML
    private TextField Description;
    @FXML
    private TextField Status;
    @FXML
    private ChoiceBox<Project> ProjectChoiceBox;

   @Override
   public void initialize(URL url, ResourceBundle rb) {
    // Populate the tournament choice box
    tournament1 = new ServiceProject();
    List<Project> tournaments = tournament1.Afficher();
    ProjectChoiceBox.getItems().addAll(tournaments);
}

//private void selectPhoto(ActionEvent event) {
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//    File selectedFile = fileChooser.showOpenDialog(null);
//    if (selectedFile != null) {
//        photoPath = selectedFile.getAbsolutePath();
//        javafx.scene.image.Image image = new javafx.scene.image.Image(new File(photoPath).toURI().toString());
//        Photo.setImage(image);
//        
//    }
//    
    
    
//}
//private void selectPhoto1(ActionEvent event) {
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//    File selectedFile = fileChooser.showOpenDialog(null);
//    if (selectedFile != null) {
//        photoPath1 = selectedFile.getAbsolutePath();
//        javafx.scene.image.Image image = new javafx.scene.image.Image(new File(photoPath1).toURI().toString());
//        Photo1.setImage(image);
//        
//    }
//}

@FXML
private void Ajouter(ActionEvent event) {
    String name;
    String description;
    String status;
    Integer project_id;
    name = Name.getText();
    description = Description.getText();
    status = Status.getText();
    project_id = ProjectChoiceBox.getValue().getId();
   
    // CT
    if (name.isEmpty() || !name.matches("^[a-zA-Z]+$")) {
        showAlert("Error", "Invalid name", "Name should only contain alphabetic characters");
        return;
    }
    
    
    if (description.isEmpty() || !description.matches("^[a-zA-Z]+$")) {
        showAlert("Error", "Invalid description", "Name should only contain alphabetic characters");
        return;
    }
    
     
    // Create Task 
    Task p =new Task(project_id,name,description,status);
    ServiceTask t= new ServiceTask();
    t.Ajouter2(p);
    
    // show alert when tournament is added successfully
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText("Task added successfully!");
    alert.showAndWait();

    // go back to TaskList.fxml
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchList.fxml"));
        Parent root = loader.load();
        MatchListController controller = loader.getController();
       // Stage stage = (Stage) name.getScene().getWindow();
       // stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading match list: " + ex.getMessage());
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