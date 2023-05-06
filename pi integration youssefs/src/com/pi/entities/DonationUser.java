package com.pi.entities;


import java.time.LocalDate;

public class DonationUser {

    private int id;
    private User user;
    private float amount;
    private LocalDate date;

    public DonationUser(int id, User user, float amount, LocalDate date) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.date = date;
    }

    public DonationUser(User user, float amount, LocalDate date) {
        this.user = user;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}