package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entites.Project;
import entites.Task;
import utils.MyDB;

public class ServiceTask implements IService<Task> {

    Connection con;

    public ServiceTask() {
        con = MyDB.createorgetInstance().getCon();
    }

    @Override
    public void Ajouter2(Task t) {
        try {
            PreparedStatement pre = con.prepareStatement("INSERT INTO `pi`.`task` (`project_id`, `name`, `status`, `description`) VALUES (?, ?, ?, ?);");
            
            pre.setInt(1, t.getProject_id());
            pre.setString(2, t.getName());
            pre.setString(3, t.getStatus());
            pre.setString(4, t.getDescription());
          
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Modifier(Task t) {
        try {
            PreparedStatement pre = con.prepareStatement("UPDATE `pi`.`task` SET `project_id`=?, `name`=?, `status`=?, `description`=? WHERE `id`=?;");
            
            pre.setInt(1, t.getProject_id());
            pre.setString(2, t.getName());
            pre.setString(3, t.getStatus());
            pre.setString(4, t.getDescription());
         
            pre.setInt(5, t.getId());
            
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Supprimer(Task t) {
        try {
            PreparedStatement pre = con.prepareStatement("DELETE FROM `pi`.`task` WHERE `id`=?;");
            
            pre.setInt(1, t.getId());
            
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public ArrayList<Task> Afficher() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement pre = con.prepareStatement("SELECT * FROM `pi`.`task` ;");
          
            ResultSet res = pre.executeQuery();
            
            while (res.next()) {
                int id = res.getInt("id");
                int project_id = res.getInt("project_id");
                String name = res.getString("name");
                String status = res.getString("status");
                String description = res.getString("description");
               
                
                Task t = new Task(id, project_id, name, status, description);
                tasks.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return tasks;
    }
    
 
public ArrayList<Task> getTasksByProjectId(int projectId) {
    ArrayList<Task> tasks = new ArrayList<>();
    try {
        PreparedStatement pre = con.prepareStatement("SELECT * FROM `pi`.`task` WHERE `project_id`=?;");
        pre.setInt(1, projectId);
        ResultSet res = pre.executeQuery();
        
        while (res.next()) {
            int id = res.getInt("id");
            String name = res.getString("name");
            String status = res.getString("status");
            String description = res.getString("description");
           
            Task t = new Task(id, projectId, name, status, description);
            tasks.add(t);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
    return tasks;
}

}
