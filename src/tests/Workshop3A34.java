/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import entites.Match;
import entites.Tournament;
import java.time.LocalDateTime;
import services.CRUDMatch;
import services.CRUDTournament;
import utils.MyDB;

/**
 *
 * @author belkn
 */
public class Workshop3A34 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyDB cc = MyDB.createorgetInstance();
         
        
        LocalDateTime startdate = LocalDateTime.of(2023, 4, 13, 10, 30, 0); // April 13, 2023 at 10:30 AM
        LocalDateTime enddate = LocalDateTime.of(2023, 4, 15, 18, 0, 0); // April 15, 2023 at 6:00 PM
        
        
        Tournament tournament = new Tournament("Tournament 1", "Description 1", "Prize 1", LocalDateTime.now(), LocalDateTime.now(), "Photo 1");
        Match match = new Match("C9","H74","FVDFV","FuuV",startdate,"dclldcnds",tournament);
        
       

       // CRUDTournament per = new CRUDTournament();
       // per.ajouterTournament(tournament);
       
        
        CRUDMatch t = new CRUDMatch();
        t.afficherMatch();
       
    }
    
}
