package GUI;

import entites.Project;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceProject;
import GUI.ImageTableCell;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import javax.swing.text.Document;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


public class ProjectListController implements Initializable {
    @FXML
    private TableView<Project> tournamentsTable;

    @FXML
    private TableColumn<Project, Integer> idColumn;

    @FXML
    private TableColumn<Project, String> nameColumn;

    @FXML
    private TableColumn<Project, String> descriptionColumn;
    @FXML
    private TableColumn<Project, String> themeColumn;
    @FXML
    private TableColumn<Project, String> locationColumn;
    
    @FXML
    private TableColumn<Project, String> statusColumn;
  
    @FXML
    private TableColumn<Project, String> photoColumn;
    @FXML
    private TableColumn<Project, Void> deleteColumn;
    @FXML
    private TableColumn<Project, Void> editColumn;
 
    @FXML
    private Button addButton;
    @FXML
    private TableColumn<Project, Void> shareColumn;
    @FXML
    private TextField searchField;

     
    @FXML
private void handleAddButton() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProject.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println("Error loading add tournament form: " + ex.getMessage());
    }
}  
@FXML
private void searchProjects(KeyEvent event) {
    String searchQuery = searchField.getText();
    FilteredList<Project> filteredData = new FilteredList<>(tournamentsTable.getItems());
    filteredData.setPredicate(tournament -> {
        if (searchQuery == null || searchQuery.isEmpty()) {
            return true;
        }
        String lowerCaseSearchQuery = searchQuery.toLowerCase();
        return tournament.getName().toLowerCase().contains(lowerCaseSearchQuery);
    });
    SortedList<Project> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(tournamentsTable.comparatorProperty());
    tournamentsTable.setItems(sortedData);
}
    @FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceProject tournament = new ServiceProject();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        //photoColumn.setCellFactory(column -> new ImageTableCell());
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("photo"));
       //edit
       editColumn.setCellFactory(column -> {
    return new TableCell<Project, Void>() {
        private final Button editButton = new Button("Edit");

        {
            editButton.setOnAction((ActionEvent event) -> {
                try {
                    Project tournament = getTableView().getItems().get(getIndex());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProject.fxml"));
                    Parent root = loader.load();
                    EditProjectController controller = loader.getController();
                    controller.setProject(tournament);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Error loading edit tournament form: " + ex.getMessage());
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : editButton);
        }
    };
});

editColumn.setCellValueFactory(new PropertyValueFactory<>("edit"));
//share
shareColumn.setCellFactory(column -> {
    return new TableCell<Project, Void>() {
        private final Button shareButton = new Button("Share");

        {
            shareButton.setOnAction((ActionEvent event) -> {
                Project tournament = getTableView().getItems().get(getIndex());
                
         String accessToken = "EAAJLcPJS6AABAA2AMOOC5d4pWo9JOtAPjkQqYy4l1aowZBAr2JiDn6Y7UCZBpwvKIupQAsbDUT1FKMNkvZBaDbvA0y9sgYYTwdpasAxMACiYny30ocuddUFog2ExZBIZAz2fATnnby4vPkmiBXJoZCSUVeYZBRTGjZBTlZAe1mLAATrlKL5u22bv9ZBnNdK8X0yZCuA5d6Qa9xJZCLwLO5JAVLuC";
        
         FacebookClient client = new DefaultFacebookClient(accessToken);

                try {
                    FacebookType response = client.publish("560249696244896" + "/photos", FacebookType.class,
                            BinaryAttachment.with("C:\\0.jpg", new FileInputStream(new File(tournament.getImage()))),
                            Parameter.with("message", tournament.getDescription()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ProjectListController.class.getName()).log(Level.SEVERE, null, ex);
                }
 });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : shareButton);
        }
    };
});

shareColumn.setCellValueFactory(new PropertyValueFactory<>("share"));

         //delete
        deleteColumn.setCellFactory(column -> {
    return new TableCell<Project, Void>() {
        private final Button deleteButton = new Button("Delete");

        {
          deleteButton.setOnAction(event -> {
    Project tournament = getTableView().getItems().get(getIndex());
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete " + tournament.getName() + "?");
    alert.setHeaderText(null);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        ServiceProject tournamentCrud = new ServiceProject();
        tournamentCrud.Supprimer(tournament);
        tournamentsTable.getItems().remove(tournament);
    }
});

        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : deleteButton);
        }
    };
});
   
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("del"));
        tournamentsTable.setItems(FXCollections.observableArrayList(tournament.Afficher()));
        addButton.setOnAction(event -> handleAddButton());

    }
    
}