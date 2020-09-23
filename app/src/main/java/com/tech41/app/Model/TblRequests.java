package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class TblRequests {

    private String friendId;
    private user friend;

    public TblRequests() {
    }

    public TblRequests(String friendId, user friend) {
        this.friendId = friendId;
        this.friend = friend;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public user getFriend() {
        return friend;
    }

    public void setFriend(user friend) {
        this.friend = friend;
    }


    @SerializedName("body")
    private String text;

}

