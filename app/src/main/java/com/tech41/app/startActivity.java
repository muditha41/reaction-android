package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dmax.dialog.SpotsDialog;


public class startActivity extends AppCompatActivity {

    TextView logo;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        logo = findViewById(R.id.mainlogo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new SpotsDialog.Builder()
                        .setContext(startActivity.this)
                        .build();
                         dialog.show();

                startActivity(new Intent(startActivity.this, LoginActivity.class));
            }
        });

    }
}