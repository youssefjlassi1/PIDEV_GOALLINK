/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.time.LocalDateTime;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class Tournament {
    private int id;
    private String name;
    private String description;
    private String prise;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private String photo;

    public Tournament() {
    }

    public Tournament(String name, String description, String prise, LocalDateTime startdate, LocalDateTime enddate, String photo) {
        this.name = name;
        this.description = description;
        this.prise = prise;
        this.startdate = startdate;
        this.enddate = enddate;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrise() {
        return prise;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrise(String prise) {
        this.prise = prise;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Tournament{" + "id=" + id + ", name=" + name + ", description=" + description + ", prise=" + prise + ", startdate=" + startdate + ", enddate=" + enddate + ", photo=" + photo + '}';
    }
        private final SimpleObjectProperty<Void> edit = new SimpleObjectProperty<>(this, "edit");

    public Void getEdit() {
        return edit.get();
    }

    public void setEdit(Void value) {
        edit.set(value);
    }

    public ReadOnlyObjectProperty<Void> editProperty() {
        return edit;
    }

}
