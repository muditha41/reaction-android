package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tech41.app.JWT.TokenManager;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.Model.user;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;

public class MyAccountActivity extends AppCompatActivity {

    TokenManager tokenManager;
    SharedPreferences preferences;

    public  MyAccountActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        getAccountDetails();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
        token = preferences.getString("keyname","");
        uId = preferences.getString("id","");

    }

    private void getAccountDetails() {

        EditText input_about = (EditText)findViewById(R.id.input_about);
        EditText input_name = (EditText)findViewById(R.id.input_name);
        Spinner input_lives_drop = (Spinner)findViewById(R.id.input_lives_drop);
        EditText input_workplace = (EditText)findViewById(R.id.input_workplace);
        Spinner input_relationship_drop = (Spinner)findViewById(R.id.input_relationship_drop);

//        input_about.setText(user.getDescription());
//        input_name.setText(user.getUserName());
//        input_lives_drop.setPrompt(user.getLocation());
//        input_workplace.setText(user.getWorkPlace());
//        input_relationship_drop.setPrompt(user.getRelationshipStatus());

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