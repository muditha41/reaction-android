package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class userStatusUpdate {
    private int UserStatusId;
    private String UserId;
    private String FriendId ;
    private int StatusId ;

    private userStatusUpdate() {
    }

    public userStatusUpdate(int userStatusId, String userId, String friendId, int statusId) {
        UserStatusId = userStatusId;
        UserId = userId;
        FriendId = friendId;
        StatusId = statusId;
    }

    public int getUserStatusId() {
        return UserStatusId;
    }

    public void setUserStatusId(int userStatusId) {
        UserStatusId = userStatusId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFriendId() {
        return FriendId;
    }

    public void setFriendId(String friendId) {
        FriendId = friendId;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }
}
