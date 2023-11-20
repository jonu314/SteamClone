package com.Bug_Tracker.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class Bug implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String bugId;
    private String bugDescription;
    private String bugType;
    private String bugLocation;
    private String bugPriority;
    private Date bugDate;
    private boolean isActive;

public Bug(){}
    public Bug(String bugId, String bugDescription, String bugLocation, String bugPriority, Date bugDate, boolean bugActive, String bugType,boolean isActive) {

        this.bugId = bugId;
        this.bugDescription = bugDescription;
        this.bugLocation = bugLocation;
        this.bugPriority = bugPriority;
        this.bugDate = bugDate;
        this.isActive = isActive;
        this.bugType = bugType;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBugId() {
        return bugId;
    }

    public void setBugId(String bugId) {
        this.bugId = bugId;
    }

    public String getBugDescription() {
        return bugDescription;
    }

    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }

    public String getBugLocation() {
        return bugLocation;
    }

    public void setBugLocation(String systemBug) {
        this.bugLocation = systemBug;
    }

    public String getBugPriority() {
        return bugPriority;
    }

    public void setBugPriority(String bugPriority) {
        this.bugPriority = bugPriority;
    }

    public Date getBugDate() {
        return bugDate;
    }

    public void setBugDate(Date bugDate) {
        this.bugDate = bugDate;
    }

    public boolean isActive() {
        return isActive;
    }

    @JsonProperty(value="isActive") // used this since it was truncating to active
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getBugType() {
        return bugType;
    }

    public void setBugType(String bugType) {
        this.bugType = bugType;
    }
}