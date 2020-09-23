package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tech41.app.JWT.TokenManager;

public class MyAccountActivity extends AppCompatActivity {

    TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


    }

    public void Logout(View view) {
        this.tokenManager = new TokenManager(MyAccountActivity.this);
        tokenManager.removeSession();
        moveToLogin();
    }
    private void moveToLogin() {
        Intent intent = new Intent(MyAccountActivity.this, StartActivity.class);
        startActivity(intent);
    }

    public void moveToMainActivity(View view) {
        Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }
}