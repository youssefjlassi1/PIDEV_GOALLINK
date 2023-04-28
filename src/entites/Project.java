/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Andrew
 */
public class Project {
    int id , owner_id;
    String name ,theme,status,description,location,image;
    Date createdAt, updatedAt ;

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", owner_id=" + owner_id + ", name=" + name + ", theme=" + theme + ", status=" + status + ", description=" + description + ", location=" + location + ", image=" + image + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

    public int getId() {
        return id;
    }

    public Project(int owner_id, String name, String theme, String status, String description, String location, String image) {
        this.owner_id = owner_id;
        this.name = name;
        this.theme = theme;
        this.status = status;
        this.description = description;
        this.location = location;
        this.image = image;
         this.createdAt = Date.valueOf(LocalDate.now());
        this.updatedAt = Date.valueOf(LocalDate.now());
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = Date.valueOf(LocalDate.now());
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
       this.updatedAt = Date.valueOf(LocalDate.now());
    }

    public Project(int id, int owner_id, String name, String theme, String status, String description, String location, String image, Date createdAt, Date updatedAt) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.theme = theme;
        this.status = status;
        this.description = description;
        this.location = location;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Project(int owner_id, String name, String theme, String status, String description, String location, String image, Date createdAt, Date updatedAt) {
        this.owner_id = owner_id;
        this.name = name;
        this.theme = theme;
        this.status = status;
        this.description = description;
        this.location = location;
        this.image = image;
      this.createdAt = Date.valueOf(LocalDate.now());
        this.updatedAt = Date.valueOf(LocalDate.now());
    }

    public Project(int id, int owner_id, String name, String theme, String status, String description, String location, String image) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.theme = theme;
        this.status = status;
        this.description = description;
        this.location = location;
        this.image = image;
        this.createdAt = Date.valueOf(LocalDate.now());
        this.updatedAt = Date.valueOf(LocalDate.now());
    }

    public Project() {
    }
}


   