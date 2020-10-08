package com.tech41.app.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.EventLogTags;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class user implements Serializable {

    private  String id;
    private String userName;
    private String fullName;
    private  String email;
    private String description =null;
    private String location=null;
    private String workPlace=null;
    private String relationshipStatus=null;
    private String image;
    private byte[] imageByte;



    public user(String id, String userName, String fullName, String email, String description, String location, String workPlace, String relationshipStatus, String image, byte[] imageByte, String text) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.description = description;
        this.location = location;
        this.workPlace = workPlace;
        this.relationshipStatus = relationshipStatus;
        this.image = image;
        this.imageByte = imageByte;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
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

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public void convertToByte(){
        if(image!=null){
            byte[] decoded = Base64.decode(image, Base64.DEFAULT);
            this.setImageByte(decoded);
          }
        }

    @SerializedName("body")
    private String text;
}


