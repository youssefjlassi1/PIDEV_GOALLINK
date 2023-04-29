package com.louay.entities;

import com.louay.utils.Constants;
import com.louay.utils.RelationObject;

public class Reponse implements Comparable<Reponse> {

    private int id;
    private RelationObject feedbackId;
    private String texte;

    public Reponse(int id, RelationObject feedbackId, String texte) {
        this.id = id;
        this.feedbackId = feedbackId;
        this.texte = texte;
    }

    public Reponse(RelationObject feedbackId, String texte) {
        this.feedbackId = feedbackId;
        this.texte = texte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(RelationObject feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }


    @Override
    public int compareTo(Reponse reponse) {
        switch (Constants.compareVar) {
            case "FeedbackId":
                return Integer.compare(reponse.getFeedbackId().getId(), this.getFeedbackId().getId());
            case "Texte":
                return reponse.getTexte().compareTo(this.getTexte());

            default:
                return 0;
        }
    }

}