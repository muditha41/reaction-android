package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tech41.app.Model.StatusIntent;
import com.tech41.app.Model.TblFriends;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Context context;
    private StatusIntent userFriend;
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
        CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);

        Intent intent = getIntent();
        userFriend = (StatusIntent) intent.getSerializableExtra("friendProfile");
        if(userFriend.getFriendId().equals(null)){

            Log.d("","friend profile details not updated yet");
        }else
        username.setText(userFriend.getFriend_fullName());
        about.setText(userFriend.getFriend_description());
        lives.setText(userFriend.getFriend_location());
        workplace.setText(userFriend.getFriend_workPlace());
        relationship_state.setText(userFriend.getFriend_relationshipStatus());

        //image decorde
        if(userFriend.getFriend_imageByte()!=null) {
            byte[] decoded = userFriend.getFriend_imageByte();
            Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
            profile_image.setImageBitmap(bitmap);
        }else {
            profile_image.setImageResource(R.drawable.ic_launcher_round);
        }
    }

    public void moveToStatus(View view) {
        Intent intent = new Intent(ProfileActivity.this, StatusActivity.class);
        intent.putExtra("userFriend", userFriend);
        startActivity(intent);
    }
}