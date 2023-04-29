package com.louay.entities;


import com.louay.utils.RelationObject;

public class Message {

    private int id;
    private RelationObject sender;
    private RelationObject receiver;
    private String message;

    public Message(int id, RelationObject sender, RelationObject receiver, String message) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Message(RelationObject sender, RelationObject receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getSender() {
        return sender;
    }

    public void setSender(RelationObject sender) {
        this.sender = sender;
    }

    public RelationObject getReceiver() {
        return receiver;
    }

    public void setReceiver(RelationObject receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}