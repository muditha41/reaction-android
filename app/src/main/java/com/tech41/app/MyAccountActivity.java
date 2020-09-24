package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tech41.app.JWT.TokenManager;
import com.tech41.app.Model.TblFriends;

public class MyAccountActivity extends AppCompatActivity {


    private Context context;
    SharedPreferences preferences;
    private TblFriends userFriend;
    TokenManager tokenManager;

    public  MyAccountActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        getAccountDetails();

    }

    private void getAccountDetails() {

        EditText input_about = (EditText)findViewById(R.id.input_about);
        EditText input_name = (EditText)findViewById(R.id.input_name);
        Spinner input_lives_drop = (Spinner)findViewById(R.id.input_lives_drop);
        EditText input_workplace = (EditText)findViewById(R.id.input_workplace);
        Spinner input_relationship_drop = (Spinner)findViewById(R.id.input_relationship_drop);



      //  input_about.setText(userFriend.getUser().getDescription());
      //  input_name.setText(userFriend.getUser().getUserName());
      //  input_lives_drop.setPrompt(userFriend.getUser().getLocation());
      //  input_workplace.setText(userFriend.getUser().getWorkPlace());
      //  input_relationship_drop.setPrompt(userFriend.getUser().getRelationshipStatus());

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

}