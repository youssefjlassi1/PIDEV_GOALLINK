/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entites.Task;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceTask;

/**
 * FXML Controller class
 *
 * @author Gamer
 */
public class EditTaskController implements Initializable {
   
    
    
   
    private Task match;
    
    private ServiceTask matchCrud;
    @FXML
    private TextField Name;
    @FXML
    private TextField Status;
    @FXML
    private TextArea Description;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        matchCrud = new ServiceTask();
        // TODO
    }    
    
     public void setMatch(Task match) {
    if (match != null) {
        this.match = match;

        // populate fields with existing data
        Name.setText(match.getName());
        Description.setText(match.getDescription());
        Status.setText(match.getStatus());
      /* startDatePicker.setValue(match.getStartdate().toLocalDate());
        endDatePicker.setValue(match.getEnddate().toLocalDate());*/
       
      
    }
   
    }
     @FXML
private void goBack(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskList.fxml"));
        Parent root = loader.load();
        TaskListController controller = loader.getController();
      //  Stage stage = (Stage) Team1.getScene().getWindow();
       // stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading match list: " + ex.getMessage());
    }
}
//  private void selectPhoto(ActionEvent event) {
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
//    
//    
//}
//  private void selectPhoto1(ActionEvent event) {
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
private void handleSaveButton() {
    // Validate input
    String name = Name.getText().trim();
    if (name.isEmpty()) {
        showAlert("Error", "Name is required", "Please enter a name for your task");
        return;
    }
    
    
    String description = Description.getText().trim();
    if (description.isEmpty()) {
        showAlert("Error", "Description is required", "Please enter a description for your task");
        return;
    }
    String status = Status.getText().trim();
    if (status.isEmpty()) {
        showAlert("Error", "Status is required", "Please enter a status for your task");
        return;
    }
    
    
    
    
    
  
    
    
    
    
    // update Project object with new data
    match.setName(name);
    match.setDescription(description);
    match.setStatus(status);
  
    
    // update Project in database
    matchCrud.Modifier(match);

    // switch to Project list view
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskList.fxml"));
        Parent root = loader.load();
        TaskListController controller = loader.getController();
      Stage stage = (Stage) Name.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading match list: " + ex.getMessage());
    }
}

    private void showAlert(String error, String name_is_required, String please_enter_a_name_for_the_Project) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
