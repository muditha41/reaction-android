package com.tech41.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tech41.app.Adapter.NotificationAdapter;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;

import java.util.List;

public class NotificationCounter {
    private TextView notificationNumber;
    private final int MAX_NUBER = 99;
    private String notification_number_counter ;
    NotificationAdapter notificationAdapter;

    public NotificationCounter(View view){
        notificationNumber =view.findViewById(R.id.notificationNumber);
    }

    public NotificationCounter(TextView notificationNumber, String notification_number_counter) {
        this.notificationNumber = notificationNumber;
        this.notification_number_counter = notification_number_counter;
    }

    public void NotificationCount(){
        notificationNumber.setText( "x");
  }
}

