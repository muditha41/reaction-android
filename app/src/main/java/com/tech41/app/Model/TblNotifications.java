package com.tech41.app.Model;

import com.google.gson.annotations.SerializedName;

public class TblNotifications {

    private int userNotificationId;
    private user friend;
    private String notification;
    private String time;
    private String state;


    public TblNotifications() {
    }

    public TblNotifications(int userNotificationId, user friend, String notification, String time, String state, String text) {
        this.userNotificationId = userNotificationId;
        this.friend = friend;
        this.notification = notification;
        this.time = time;
        this.state = state;
        this.text = text;
    }

    public int getUserNotificationId() {
        return userNotificationId;
    }

    public void setUserNotificationId(int userNotificationId) {
        this.userNotificationId = userNotificationId;
    }

    public user getFriend() {
        return friend;
    }

    public void setFriend(user friend) {
        this.friend = friend;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
