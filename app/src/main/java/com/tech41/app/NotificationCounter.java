package com.tech41.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tech41.app.Adapter.NotificationAdapter;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;

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
        Api api = RetrofitClient.getInstance().create(Api.class);
        Call<String> call = api.getNotificationCount("Bearer "+token,uId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String x = response.body().toString();
                    notificationNumber.setText(x);
                }
                else
                    try {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        String e = (obj.getString("message"));
                    } catch (Exception e) { }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
  }
}

