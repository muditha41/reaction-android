package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class TblFriends {

    private String userFirendId;
    private String userId;
    private String friendId;
    private String status;
    private String lastUpdated;
    private String profileImage;

    public TblFriends() {
    }

    public TblFriends(String userFirendId, String userId, String friendId, String status, String lastUpdated, String profileImage) {
        this.userFirendId = userFirendId;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.lastUpdated = lastUpdated;
        this.profileImage = profileImage;
    }

    public String getUserFirendId() {
        return userFirendId;
    }

    public void setUserFirendId(String userFirendId) {
        this.userFirendId = userFirendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

@SerializedName("body")
    private String text;

}
