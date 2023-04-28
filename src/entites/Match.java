/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.time.LocalDateTime;
import entites.Tournament;
/**
 *
 * @author Gamer
 */
public class Match {
    private int id;
    private String team1;
    private String team2;
    private String team1photo;
    private String team2photo;
    private LocalDateTime startdate;
    private String matchwinner;
    private Tournament tournament;

    public Match() {
        
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam1photo() {
        return team1photo;
    }

    public void setTeam1photo(String team1photo) {
        this.team1photo = team1photo;
    }

    public String getTeam2photo() {
        return team2photo;
    }

    public void setTeam2photo(String team2photo) {
        this.team2photo = team2photo;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public String getMatchwinner() {
        return matchwinner;
    }

    public void setMatchwinner(String matchwinner) {
        this.matchwinner = matchwinner;
    }
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Match( String team1, String team2, String team1photo, String team2photo, LocalDateTime startdate, String matchwinner, Tournament tournament) {
        
        this.team1 = team1;
        this.team2 = team2;
        this.team1photo = team1photo;
        this.team2photo = team2photo;
        this.startdate = startdate;
        this.matchwinner = matchwinner;
        this.tournament = tournament;
    }
    
     @Override
    public String toString() {
        return "Match{" + "id=" + id + ", team1=" + team1 + ", team2=" + team2 + ", team1photo=" + team1photo + ", team2photo=" + team2photo + ", startdate=" + startdate + ", matchwinner=" + matchwinner + '}';
    }
    
}

