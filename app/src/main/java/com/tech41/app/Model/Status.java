package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class Status {

    private int statusId ;
    private String name ;
    private String image ;

    public Status() {
    }

    public Status(int statusId, String name, String image, String text) {
        this.statusId = statusId;
        this.name = name;
        this.image = image;
        this.text = text;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
