package com.pi.entities;


import com.pi.utils.RelationObject;

import java.time.LocalDate;

public class Project {

    private int id;
    private RelationObject owner;
    private String name;
    private String theme;
    private String status;
    private String image;
    private String description;
    private String location;
    private LocalDate created;
    private LocalDate updated;

    public Project(int id, RelationObject owner, String name, String theme, String status, String image, String description, String location, LocalDate created, LocalDate updated) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.theme = theme;
        this.status = status;
        this.image = image;
        this.description = description;
        this.location = location;
        this.created = created;
        this.updated = updated;
    }

    public Project(RelationObject owner, String name, String theme, String status, String image, String description, String location, LocalDate created, LocalDate updated) {
        this.owner = owner;
        this.name = name;
        this.theme = theme;
        this.status = status;
        this.image = image;
        this.description = description;
        this.location = location;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getOwner() {
        return owner;
    }

    public void setOwner(RelationObject owner) {
        this.owner = owner;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }


}