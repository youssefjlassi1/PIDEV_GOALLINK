/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import entites.Project;
import entites.Task;
import utils.MyDB;

/**
 *
 * @author Andrew
 */
public class ServiceProject  implements IService<Project>{
    
    Connection con ; 
    Statement ste;
     
    
    
    
    
    
    public ServiceProject() {
        
        con = MyDB.createorgetInstance().getCon();
        
    }

 

    @Override
    public void Ajouter2(Project t) {
        try {
            PreparedStatement pre = con.prepareStatement("INSERT INTO `pi`.`project` (`owner_id`,`name`,`theme`,`status`,`image`,`description`,`location`,`created`,`updated`) VALUES (?,?,?,?,?,?,?,?,?);");
            
            pre.setInt(1, t.getOwner_id());
            pre.setString(2, t.getName());
            pre.setString(3, t.getTheme());
            pre.setString(4, t.getStatus());
            pre.setString(5, t.getImage());
            pre.setString(6, t.getDescription());
            pre.setString(7, t.getLocation());
             pre.setDate(8, t.getCreatedAt());
              pre.setDate(9, t.getUpdatedAt());
            
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

      @Override
    public void Modifier(Project t) {
        try {
            PreparedStatement pre = con.prepareStatement("UPDATE `pi`.`project` SET `owner_id`=?, `name`=?, `theme`=?, `status`=?, `image`=?, `description`=?, `location`=?, `updated`=? WHERE `id`=?;");
            
            pre.setInt(1, t.getOwner_id());
            pre.setString(2, t.getName());
            pre.setString(3, t.getTheme());
            pre.setString(4, t.getStatus());
            pre.setString(5, t.getImage());
            pre.setString(6, t.getDescription());
            pre.setString(7, t.getLocation());
            pre.setDate(8, t.getUpdatedAt());
            pre.setInt(9, t.getId());
            
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Supprimer(Project t) {
        try {
            PreparedStatement pre = con.prepareStatement("DELETE FROM `pi`.`project` WHERE `id`=?;");
            
            pre.setInt(1, t.getId());
            
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


  /*  @Override
    public ArrayList<Project> Afficher() {
        ArrayList<Project> pers = new ArrayList<>();
        try {
            ste =con.createStatement();
            String req = "SELECT * FROM `personne`";
            ResultSet res =ste.executeQuery(req);
            
            while(res.next()){
                int id = res.getInt("id");
                String nom = res.getString(2);
                String prenom =res.getString("prenom");
                int age = res.getInt(4);
                
                Project p = new Project(id, nom, prenom, age);
                pers.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return  pers;
       
    }*/

      @Override
    public ArrayList<Project> Afficher() {
        ArrayList<Project> projets = new ArrayList<>();
        try {
            PreparedStatement pre = con.prepareStatement("SELECT * FROM `pi`.`project`;");
            ResultSet res = pre.executeQuery();
            
            while(res.next()){
                int id = res.getInt("id");
                int owner_id = res.getInt("owner_id");
                String name = res.getString("name");
                String theme = res.getString("theme");
                String status = res.getString("status");
                String image = res.getString("image");
                String description = res.getString("description");
                String location = res.getString("location");
                java.sql.Date created_at = res.getDate("created");
                java.sql.Date updated_at = res.getDate("updated");
                
                Project p = new Project(id, owner_id, name, theme, status,  description, location,image, created_at, updated_at);
                projets.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return projets;
    }
    
    
    public ArrayList<Task> Afficher(Project p) {
      ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement pre = con.prepareStatement("SELECT * FROM `pi`.`task` WHERE `project_id`=1;");
       //     pre.setInt(1, 1);
        ResultSet res = pre.executeQuery();
        
        while (res.next()) {
            int id = res.getInt("id");
          
            String name = res.getString("name");
            String status = res.getString("status");
            String description = res.getString("description");
           
            Task t = new Task(id, p.getId(), name, status, description);
            tasks.add(t);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
    return tasks;
    
}
    public Project getProdbyN(String produit) {
            Project t = new Project();         
    try {
        String query = "SELECT * FROM tournament WHERE name = ?";
         ServiceProject cat = new ServiceProject();
         
        PreparedStatement pre = con.prepareStatement(query);
        pre.setString(1, produit);
        ResultSet rs = pre.executeQuery();
                    while (rs.next()) {
                
                t.setId(rs.getInt(1));
                t.setName(rs.getString("name"));
                t.setLocation(rs.getString("location"));
                t.setDescription(rs.getString("description"));
                t.setImage(rs.getString("image"));
                
                    }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return t;
        }

}
