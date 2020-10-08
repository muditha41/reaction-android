package com.tech41.app.Model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusIntent implements Serializable {

    private String userId;
    private String user_userName;
    private byte[] user_imageByte;
    private String friendId;
    private String friend_userName;
   private byte[] friend_imageByte;
    private String friend_fullName;
    private String friend_description;
    private String friend_location;
    private String friend_workPlace;
    private String friend_relationshipStatus;
    private String inviteStatus;
    private userStatus userStatus;


    public StatusIntent(String friendId, String userId, String userName, byte[] imageByte, String userName1, byte[] imageByte1, String fullName, String description, String location, String workPlace, String relationshipStatus, String inviteStatus, com.tech41.app.Model.userStatus userStatus) {

        this.friendId = friendId;
        this.userId = userId;
        this.user_userName = userName;
        this.user_imageByte = imageByte;
        this.friend_userName = userName1;
        this.friend_imageByte = imageByte1;
        this.friend_fullName = fullName;
        this.friend_description = description;
        this.friend_location = location;
        this.friend_workPlace = workPlace;
        this.friend_relationshipStatus = relationshipStatus;
        this.inviteStatus = inviteStatus;
        this.userStatus = userStatus;

    }

    public String getUserId() {
        return userId;
    }

    public String getUser_userName() {
        return user_userName;
    }

    public byte[] getUser_imageByte() {
        return user_imageByte;
    }

    public String getFriendId() {
        return friendId;
    }

    public String getFriend_userName() {
        return friend_userName;
    }

    public byte[] getFriend_imageByte() {
        return friend_imageByte;
    }

    public String getFriend_fullName() {
        return friend_fullName;
    }

    public String getFriend_description() {
        return friend_description;
    }

    public String getFriend_location() {
        return friend_location;
    }

    public String getFriend_workPlace() {
        return friend_workPlace;
    }

    public String getFriend_relationshipStatus() {
        return friend_relationshipStatus;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public com.tech41.app.Model.userStatus getUserStatus() {
        return userStatus;
    }

    @SerializedName("body")
    private String text;

}
