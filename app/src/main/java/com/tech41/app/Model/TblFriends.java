package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class TblFriends {

    private int userFriendId;
    private String userId;
    private user user;
    private String friendId;
    private user friend;
    private String inviteStatus;

    public TblFriends() {
    }

    public TblFriends(int userFriendId, String userId, user user, String friendId, user friend, String inviteStatus, String text) {
        this.userFriendId = userFriendId;
        this.userId = userId;
        this.user = user;
        this.friendId = friendId;
        this.friend = friend;
        this.inviteStatus = inviteStatus;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("body")
    private String text;

}
