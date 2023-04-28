package GUI;

import entites.Project;
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
import services.ServiceProject;

public class EditProjectController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField prizeTextField;
   
 
        @FXML
    private ImageView Photo;
    
    
    private String photoPath;
    private Project tournament;
    
    private ServiceProject tournamentCrud;
    
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tournamentCrud = new ServiceProject();
    }

   public void setTournament(Project tournament) {
    if (tournament != null) {
        this.tournament = tournament;

        // populate fields with existing data
        nameTextField.setText(tournament.getName());
        descriptionTextArea.setText(tournament.getDescription());
        prizeTextField.setText(tournament.getTheme());
    
       
        photoPath = tournament.getImage();
        javafx.scene.image.Image image = new javafx.scene.image.Image(new File(photoPath).toURI().toString());
        Photo.setImage(image);}
   
    }
    @FXML
private void goBack(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TournamentList.fxml"));
        Parent root = loader.load();
        ProjectListController controller = loader.getController();
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading tournament list: " + ex.getMessage());
    }
}

    @FXML
private void handleSaveButton() {
    // Validate input
    String name = nameTextField.getText().trim();
    if (name.isEmpty()) {
        showAlert("Error", "Name is required", "Please enter a name for the tournament");
        return;
    }
    
    String description = descriptionTextArea.getText().trim();
    if (description.isEmpty()) {
        showAlert("Error", "Description is required", "Please enter a description for the tournament");
        return;
    }
    
    String prize = prizeTextField.getText().trim();
    if (prize.isEmpty()) {
        showAlert("Error", "Prize is required", "Please enter a prize for the tournament");
        return;
    }
    
    
    
    // update tournament object with new data
    tournament.setName(name);
    tournament.setDescription(description);
    tournament.setTheme(prize);
    tournament.setImage(photoPath);
   
    
    // update tournament in database
    tournamentCrud.Modifier(tournament);

    // switch to tournament list view
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectList.fxml"));
        Parent root = loader.load();
        ProjectListController controller = loader.getController();
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading tournament list: " + ex.getMessage());
    }
}


    private void handleCancelButton() throws IOException {
       try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectList.fxml"));
        Parent root = loader.load();
        ProjectListController controller = loader.getController();
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException ex) {
        System.out.println("Error loading tournament list: " + ex.getMessage());
    }
        
    }
    private void showAlert(String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}

}
