package com.pi.entities;

public class User {

    private int id;
    private String email;

    public User(int id, String name) {
        this.id = id;
        this.email = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}