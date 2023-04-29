/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entites.Match;
import entites.Tournament;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeamRankingsController implements Initializable {

    @FXML
    private TableView<RankingTableRow> rankingsTable;

    @FXML
    private TableColumn<RankingTableRow, Integer> rankColumn;

    @FXML
    private TableColumn<RankingTableRow, String> teamColumn;

    @FXML
    private TableColumn<RankingTableRow, Integer> winsColumn;

    private List<Match> TaskList;

    private List<Tournament> tournamentList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the columns in the table
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        // Populate the table with the rankings data
        rankingsTable.getItems().setAll(getRankingsData());
    }

    /**
     * Returns a list of ranking table rows, where each row contains the team name
     * and the number of wins that team has in all matches across all tournaments.
     */
    private List<RankingTableRow> getRankingsData() {
        Map<String, Integer> teamWinsMap = new HashMap<>();

        // Loop through all the matches and count the wins for each team
        TaskList.stream().map((match) -> match.getMatchwinner()).filter((winner) -> (winner != null && !winner.isEmpty())).forEachOrdered((winner) -> {
            int wins = teamWinsMap.getOrDefault(winner, 0);
            teamWinsMap.put(winner, wins + 1);
        });

        // Convert the team wins map to a list of ranking table rows and sort it by wins
        List<RankingTableRow> rankingsData = new ArrayList<>();

        teamWinsMap.keySet().forEach((team) -> {
            int wins = teamWinsMap.get(team);
            rankingsData.add(new RankingTableRow(team, wins));
        });

        Collections.sort(rankingsData);
        assignRanks(rankingsData);

        return rankingsData;
    }

    /**
     * Assigns a rank to each ranking table row based on the number of wins, with the
     * team with the most wins being ranked #1.
     */
    private void assignRanks(List<RankingTableRow> rankingsData) {
        int rank = 1;
        int currentWins = -1;

        for (RankingTableRow row : rankingsData) {
            if (row.getWins() < currentWins) {
                rank++;
            }

            row.setRank(rank);
            currentWins = row.getWins();
        }
    }

    /**
     * Sets the list of matches for this controller.
     */
    public void setTaskList(List<Match> TaskList) {
        this.TaskList = TaskList;
    }

    /**
     * Sets the list of tournaments for this controller.
     */
    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    /**
     * A class representing a row in the team rankings table.
     */
    public static class RankingTableRow implements Comparable<RankingTableRow> {

        private int rank;
        private String team ;
        private int wins;

    public RankingTableRow(String team, int wins) {
        this.team = team;
        this.wins = wins;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public int compareTo(RankingTableRow other) {
        // Compare the number of wins, with the team with the most wins being ranked higher
        return Integer.compare(other.wins, this.wins);
    }

}
}