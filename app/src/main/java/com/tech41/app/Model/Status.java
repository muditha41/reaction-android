package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class Status {
    private int StatusId ;
    private String Name ;
    private String Image ;

    public Status() {
    }

    public Status(int statusId, String name, String image) {
        StatusId = statusId;
        Name = name;
        Image = image;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @SerializedName("body")
    private String text;
}
