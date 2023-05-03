package com.example.demo;
import java.sql.Date;

public class User {
    private Integer memberId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNum;
    private String position;
    private String image;
    private Date date;

    private String email;
    private Double salary;

    public User(Integer memberId, String firstName, String lastName, String gender, String phoneNum, String position, String image, Date date, String email){
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.position = position;
        this.image = image;
        this.date = date;
        this.email = email;
    }


    public Integer getMemberId(){
        return memberId;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public String getPosition(){
        return position;
    }
    public String getImage(){
        return image;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public Date getDate(){
        return date;
    }
    public Double getSalary(){
        return salary;
    }


}