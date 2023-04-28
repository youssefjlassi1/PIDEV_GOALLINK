package GUI;

import entites.Project;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceProject;

/**
 * FXML Controller class
 *
 * @author elroug3
 */
public class TournamentsHomePage implements Initializable {

    private ObservableList<Project> tournaments;
    ServiceProject tor = new ServiceProject();
        private String torLib;
    @FXML
    private GridPane grid;
   @FXML
    private Button azeazezaezaezaez;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      tournaments = (ObservableList<Project>) tor.Afficher();
        try {
            show(tournaments);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TournamentsHomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public void show(ObservableList<Project> tournaments) throws FileNotFoundException {
    //    name.setCellValueFactory(new PropertyValueFactory<Tournament,String>("name"));
     //   desc.setCellValueFactory(new PropertyValueFactory<Tournament,String>("desription"));           
    //    photo.setCellValueFactory(new PropertyValueFactory<Tournament,Image>("image"));
    //    table.setItems(tournaments);
        int col, row;
        row = 0;
        col = 0;
        ColumnConstraints column1 = new ColumnConstraints(200);
        ColumnConstraints column2 = new ColumnConstraints(200);
        ColumnConstraints column3 = new ColumnConstraints(200);
        
        grid.getRowConstraints().add(new RowConstraints(200));
        grid.getColumnConstraints().addAll(column1, column2, column3);
        grid.setHgap(10);
        grid.setVgap(10);
        for (Project tournament : tournaments) {

            if (col < 4) {
                Label T = new Label();
                T.setText(tournament.getName());
                FileInputStream input = new FileInputStream(tournament.getImage());
                Image image = new Image(input);


                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(150);
                VBox vbox = new VBox();
                vbox.getChildren().addAll(imageView,T );
                Pane pane = new Pane(vbox);
                
                
                pane.setOnMouseClicked(event -> {
                    // Toggle the selection state of the pane
//                    if (pane.getStyleClass().contains("selected")) {
//                        pane.getStyleClass().remove("selected");
//                        pane.setStyle("-fx-background-color: #D2CECE;");
//
//                    } else {
                        pane.getStyleClass().add("selected");
                        pane.setStyle("-fx-background-color: #FFFFFF;");
                        for (Node node : pane.getChildren()) {
                            System.out.println("node Pane");
                            if (node instanceof VBox) {
                                System.out.println("node vbox");
                                VBox vboxx = (VBox) node;
                                for (Node nodee : vboxx.getChildren()) {
                                    System.out.println("vbox 1");
                                    if (nodee instanceof Label) {
                                        System.out.println("node label");
                                        Label lab = (Label) nodee;
                                        torLib = lab.getText();
                                        System.out.println("aaaaaaaaaaaaaaa" + torLib);
                                        break;
                                    }

                                }
                            }
                        }
                        for (Node otherNode : grid.getChildren()) {
                            if (otherNode instanceof Pane && otherNode != pane) {
                                otherNode.getStyleClass().remove("selected");
                                otherNode.setStyle("-fx-background-color: #D2CECE;");
                                
                            }
                        }
                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                showDetails(stage);
                            } catch (IOException ex) {
                                Logger.getLogger(TournamentsHomePage.class.getName()).log(Level.SEVERE, null, ex);
                            }
    }
                            
//                    }
                });
                pane.setStyle("-fx-background-color: #D2CECE;");
                grid.add(pane, col, row);
                col++;
                if (col == 6) {
                    col = 0;
                    row++;
                    grid.getRowConstraints().add(new RowConstraints(200));
                }
            }
        }

    }
    public void showDetails(Stage stage)throws IOException {
                Project selectedprod = tor.getProdbyN(torLib);
        if (selectedprod != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TournamentDetails.fxml"));
            Parent root = loader.load();
            TournamentDetailsController controller = loader.getController();
            controller.show(selectedprod);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void Home(ActionEvent event) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    
    
}