package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class user implements Serializable {

    private  String id;
    private String userName;
    private  String email;

    public user() {
    }

    public user(String id, String userName, String email, String text) {
        this.id = id;
        this.userName = userName;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("body")
    private String text;
}


