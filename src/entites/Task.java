/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author youss
 */
public class Task {
    int id;
     private int project_id;
    String name ,status,description;

    public Task(int id, int project_id, String name, String status, String description) {
        this.id = id;
        this.project_id = project_id;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public Task(int project_id, String name, String status, String description) {
        this.project_id = project_id;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", project_id=" + getProject_id() + ", name=" + name + ", status=" + status + ", description=" + description + '}';
    }
 


    
}
