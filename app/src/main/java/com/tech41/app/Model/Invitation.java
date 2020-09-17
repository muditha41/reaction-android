package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class Invitation {

   private String userId;
   private  String email;

    public Invitation() {
    }

    public Invitation(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SerializedName("body")
    private String text;
}
