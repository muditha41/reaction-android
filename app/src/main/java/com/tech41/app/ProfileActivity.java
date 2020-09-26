package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tech41.app.Model.TblFriends;

public class ProfileActivity extends AppCompatActivity {
    private Context context;
    private TblFriends userFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getFriendDetails();

    }

    private void getFriendDetails() {

        TextView username = (TextView)findViewById(R.id.username);
        TextView about = (TextView)findViewById(R.id.about);
        TextView lives = (TextView)findViewById(R.id.lives);
        TextView workplace = (TextView)findViewById(R.id.workplace);
        TextView relationship_state = (TextView)findViewById(R.id.relationship_state);

        Intent intent = getIntent();
        userFriend = (TblFriends) intent.getSerializableExtra("friendProfile");
    //  TblFriends tblFriends = new TblFriends();
        if(userFriend.getFriendId().equals(null)){

            Log.d("","friend profile details not updated yet");
        }else
        username.setText(userFriend.getFriend().getUserName());
        about.setText(userFriend.getFriend().getDescription());
        lives.setText(userFriend.getFriend().getLocation());
        workplace.setText(userFriend.getFriend().getWorkPlace());
        relationship_state.setText(userFriend.getFriend().getRelationshipStatus());
    }



    public void moveToStatus(View view) {
        Intent intent = new Intent(ProfileActivity.this, StatusActivity.class);
        intent.putExtra("userFriend", userFriend);
        startActivity(intent);
    }
}