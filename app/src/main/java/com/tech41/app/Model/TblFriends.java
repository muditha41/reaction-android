package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TblFriends implements Serializable {

    private int userFriendId;
    private String userId;
    private user user;
    private String friendId;
    private user friend;
    private String inviteStatus;
    private userStatus userStatus;

    public TblFriends() {
    }

    public TblFriends(int userFriendId, String userId, com.tech41.app.Model.user user, String friendId, com.tech41.app.Model.user friend, String inviteStatus, userStatus userStatus, String text) {
        this.userFriendId = userFriendId;
        this.userId = userId;
        this.user = user;
        this.friendId = friendId;
        this.friend = friend;
        this.inviteStatus = inviteStatus;
        this.userStatus = userStatus;
        this.text = text;
    }

    public int getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(int userFriendId) {
        this.userFriendId = userFriendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public com.tech41.app.Model.user getUser() {
        return user;
    }

    public void setUser(com.tech41.app.Model.user user) {
        this.user = user;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public com.tech41.app.Model.user getFriend() {
        return friend;
    }

    public void setFriend(com.tech41.app.Model.user friend) {
        this.friend = friend;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public userStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(userStatus userStatus) {
        this.userStatus = userStatus;
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
