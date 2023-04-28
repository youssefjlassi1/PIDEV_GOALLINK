/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import entites.Match;
import java.util.List;

/**
 *
 * @author Gamer
 */
public interface InterfaceMatch {
    
    public void ajouterMatch(Match p);
    public void modifierMatch(Match p);
    public void supprimerMatch(Match p);
    public List<Match> afficherMatch();
    
}
