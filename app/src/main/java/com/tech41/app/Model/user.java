package com.tech41.app.Model;

import android.util.EventLogTags;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class user implements Serializable {

    private  String id;
    private String userName;
    private String fullName;
    private  String email;
    private String description;
    private String location;
    private String workPlace;
    private String relationshipStatus;
    private String image;

    public user(String id, String userName, String fullName, String email, String description, String location, String workPlace, String relationshipStatus, String image, String text) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.description = description;
        this.location = location;
        this.workPlace = workPlace;
        this.relationshipStatus = relationshipStatus;
        this.image = image;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @SerializedName("body")
    private String text;
}


