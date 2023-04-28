/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import entites.Match;
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
import services.ServiceTask;
import GUI.ImageTableCell;

import entites.Task;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

/**
 * FXML Controller class
 *
 * @author Gamer
 */
public class MatchListController implements Initializable {
    
    
    @FXML
    private TableView<Task> matchTable;

    @FXML
    private TableColumn<Task, Integer> idColumn;
  
    @FXML
    private TableColumn<Task, Void> deleteColumn;
    @FXML
    private TableColumn<Task, Void> editColumn; 
     @FXML
    private Button addButton;
    @FXML
    private TableColumn<Task, String> NameColumn;
    @FXML
    private TableColumn<Task, String> DescriptionColumn;
    @FXML
    private TableColumn<Task, String> statusColumn;
    @FXML
private void handleAddButton() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMatch.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println("Error loading add match form: " + ex.getMessage());
    }
}
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceTask match = new ServiceTask();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
        
   //editMatch
    
        editColumn.setCellFactory(column -> {
    return new TableCell<Task, Void>() {
        private final Button editButton = new Button("Edit");

        {
            editButton.setOnAction((ActionEvent event) -> {
                try {
                    Task match = getTableView().getItems().get(getIndex());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditMatch.fxml"));
                    Parent root = loader.load();
                    EditMatchController controller = loader.getController();
                    controller.setMatch(match);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Error loading edit match form: " + ex.getMessage());
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


         //delete
        deleteColumn.setCellFactory(column -> {
    return new TableCell<Task, Void>() {
        private final Button deleteButton = new Button("Delete");

        {
          deleteButton.setOnAction(event -> {
    Task match = getTableView().getItems().get(getIndex());
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete " + match.getName()+ "?");
    alert.setHeaderText(null);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        ServiceTask matchCrud = new ServiceTask();
        matchCrud.Supprimer(match);
        matchTable.getItems().remove(match);
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
        matchTable.setItems(FXCollections.observableArrayList(match.Afficher()));
        addButton.setOnAction(event -> handleAddButton());

    }
   

    
}
    

    


