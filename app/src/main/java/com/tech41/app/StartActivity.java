package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tech41.app.JWT.TokenManager;

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;


public class StartActivity extends AppCompatActivity {

    Timer timer;
    TokenManager tokenManager;

    @Override
    protected void onStart() {
            super.onStart();
            checkSession();
        }

        private void checkSession() {
            //check session
            this.tokenManager = new TokenManager(StartActivity.this);
            String KeyName = tokenManager.getSession();

            if(KeyName!="0"){
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