package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class userStatus implements Serializable {

    private int userStatusId;
    private int statusId;
    private String statusTimeStamp;
    private String statusState;
    private Status status;
    private int friendStatusId;
    private String friendStatusTimeStamp;
    private Status friendStatus;
    private String userFriend;


    public userStatus() {
    }

    public userStatus(int userStatusId, int statusId, String statusTimeStamp, String statusState, Status status, int friendStatusId, String friendStatusTimeStamp, Status friendStatus, String userFriend, String text) {
        this.userStatusId = userStatusId;
        this.statusId = statusId;
        this.statusTimeStamp = statusTimeStamp;
        this.statusState = statusState;
        this.status = status;
        this.friendStatusId = friendStatusId;
        this.friendStatusTimeStamp = friendStatusTimeStamp;
        this.friendStatus = friendStatus;
        this.userFriend = userFriend;
        this.text = text;
    }

    public int getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(int userStatusId) {
        this.userStatusId = userStatusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusTimeStamp() {
        return statusTimeStamp;
    }

    public void setStatusTimeStamp(String statusTimeStamp) {
        this.statusTimeStamp = statusTimeStamp;
    }

    public String getStatusState() {
        return statusState;
    }

    public void setStatusState(String statusState) {
        this.statusState = statusState;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getFriendStatusId() {
        return friendStatusId;
    }

    public void setFriendStatusId(int friendStatusId) {
        this.friendStatusId = friendStatusId;
    }

    public String getFriendStatusTimeStamp() {
        return friendStatusTimeStamp;
    }

    public void setFriendStatusTimeStamp(String friendStatusTimeStamp) {
        this.friendStatusTimeStamp = friendStatusTimeStamp;
    }

    public Status getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Status friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(String userFriend) {
        this.userFriend = userFriend;
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
