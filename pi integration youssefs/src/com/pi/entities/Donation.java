package com.pi.entities;

import com.pi.utils.Constants;

import java.time.LocalDate;

public class Donation implements Comparable<Donation> {

    private int id;
    private Project project;
    private float goalAmount;
    private float recivedAmount;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Donation(int id, Project project, float goalAmount, float recivedAmount, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.project = project;
        this.goalAmount = goalAmount;
        this.recivedAmount = recivedAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Donation(Project project, float goalAmount, float recivedAmount, LocalDate createdAt, LocalDate updatedAt) {
        this.project = project;
        this.goalAmount = goalAmount;
        this.recivedAmount = recivedAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public float getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(float goalAmount) {
        this.goalAmount = goalAmount;
    }

    public float getRecivedAmount() {
        return recivedAmount;
    }

    public void setRecivedAmount(float recivedAmount) {
        this.recivedAmount = recivedAmount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public int compareTo(Donation donation) {
        switch (Constants.compareVar) {
            case "Project":
                return Integer.compare(donation.getProject().getId(), this.getProject().getId());
            case "GoalAmount":
                return Float.compare(donation.getGoalAmount(), this.getGoalAmount());
            case "RecivedAmount":
                return Float.compare(donation.getRecivedAmount(), this.getRecivedAmount());
            case "CreatedAt":
                return donation.getCreatedAt().compareTo(this.getCreatedAt());
            case "UpdatedAt":
                return donation.getUpdatedAt().compareTo(this.getUpdatedAt());

            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return project.getName();
    }
    public Donation(float amount, Donation donation) {
    this.goalAmount = amount;
    this.project = project;
    
    
}
}