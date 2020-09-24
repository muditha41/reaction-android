package com.tech41.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tech41.app.Model.TblNotifications;

import java.util.List;

public class NotificationCounter {
    private TextView notificationNumber;
    private final int MAX_NUBER = 99;
    private int notification_number_counter = 1;

    public NotificationCounter(View view){
        notificationNumber =view.findViewById(R.id.notificationNumber);
    }

    public void Count( ){

////        if(count>MAX_NUBER){
//            Log.d("Counter","Maximum Number Reached!");
//        }else {
//            notificationNumber.setText(String.valueOf(count));
//        }
    }
}

