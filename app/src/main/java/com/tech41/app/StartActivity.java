package com.tech41.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tech41.app.JWT.JWTUtils;
import com.tech41.app.JWT.TokenManager;

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;


public class StartActivity extends AppCompatActivity {

    Timer timer;
    TokenManager tokenManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
            super.onStart();
        try {
            checkSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void checkSession() throws Exception {
            //check session
            this.tokenManager = new TokenManager(StartActivity.this);
            String KeyName = tokenManager.getSession();

            if(KeyName!=null){
                tokenManager.createLoginSession(KeyName, JWTUtils.decordeJWT(KeyName));
                moveToMainActivity();
            }else {
                moveToLogin();
            }
        }

    private void moveToLogin() {
        //Timer for app loading
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run(){
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); } },2000);
    }

    private void moveToMainActivity() {
        //Timer for app loading
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run(){
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); } },2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

}