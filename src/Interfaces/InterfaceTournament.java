/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import entites.Tournament;
import java.util.List;

/**
 *
 * @author belkn
 */
public interface InterfaceTournament {
    
    public void ajouterTournament(Tournament p);
    public void modifierTournament(Tournament p);
    public void supprimerTournament(Tournament p);
    public List<Tournament> afficherTournament();
    
}
