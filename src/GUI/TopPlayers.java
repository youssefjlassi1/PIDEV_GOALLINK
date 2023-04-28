/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

    
import entites.Player;

    import java.io.IOException;
    import java.net.URL;
    import java.text.ParseException;
import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.util.ResourceBundle;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import javafx.beans.value.ObservableValue;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.control.Label;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.layout.VBox;

    import services.CRUDTournament;

   
    public class TopPlayers implements Initializable {

    @FXML
    private TableView<Player> leaderboardTable;
    
    @FXML
    private TableColumn<TopPlayers, String> nameColumn;

    @FXML
    private TableColumn<TopPlayers, Integer> rankColumn;

    @FXML
    private TableColumn<TopPlayers, Integer> lpColumn;
    



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CRUDTournament lead = new CRUDTournament();

            List<Player> leaderboardData = lead.getTop5Leaderboard();

            List<Player> players = new ArrayList<>();

            for (Player data : leaderboardData) {
                String name = (String) data.getName();
                int rank = (int) data.getRank();
                int lp = (int) data.getLp();
                players.add(new Player(name, rank, lp));
            }

            ObservableList<Player> leaderboardObservableList = FXCollections.observableArrayList(players);

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
            lpColumn.setCellValueFactory(new PropertyValueFactory<>("lp"));

            leaderboardTable.setItems(leaderboardObservableList);
            //teams
           

        
            } catch (Exception ex) {
            
         }
    }
}
