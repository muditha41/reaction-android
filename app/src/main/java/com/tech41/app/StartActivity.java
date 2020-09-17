package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;


public class StartActivity extends AppCompatActivity {

    TextView logo;
    Timer timer;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Timer for app loading
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run(){
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

              }
            },2000);




        logo = findViewById(R.id.mainlogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new SpotsDialog.Builder()
                        .setContext(StartActivity.this)
                        .build();
                         dialog.show();

                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

    }
}