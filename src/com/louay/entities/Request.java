package com.louay.entities;


import com.louay.utils.RelationObject;

import java.time.LocalDate;

public class Request {

    private int id;
    private RelationObject userId;
    private String sujet;
    private String email;
    private String description;
    private LocalDate date;
    private int status;

    public Request(int id, RelationObject userId, String sujet, String email, String description, LocalDate date, int status) {
        this.id = id;
        this.userId = userId;
        this.sujet = sujet;
        this.email = email;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public Request(RelationObject userId, String sujet, String email, String description, LocalDate date, int status) {
        this.userId = userId;
        this.sujet = sujet;
        this.email = email;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getUserId() {
        return userId;
    }

    public void setUserId(RelationObject userId) {
        this.userId = userId;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}