/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

public class Player {
    private String name;
    private int rank;
    private int lp;
    
    public Player(String name, int rank, int lp) {
        this.name = name;
        this.rank = rank;
        this.lp = lp;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public int getLp() {
        return lp;
    }
    
    public void setLp(int lp) {
        this.lp = lp;
    }
}


