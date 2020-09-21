package com.tech41.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblRequests;

import java.util.List;

public class StatusActivity extends AppCompatActivity {

    private List<TblRequests> requestsResposeList;
    private Context context;
    SharedPreferences preferences;
    TextView tvSelected,empty_status;
    private List<TblFriends> friendsResposeList;
    private TblFriends userFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        TextView friend_name = (TextView)findViewById(R.id.friend_name);
        TextView friend_status_id = (TextView)findViewById(R.id.friend_status_id);
        TextView user_status_id = (TextView)findViewById(R.id.user_status_id);
        LottieAnimationView friend_status_image = (LottieAnimationView)findViewById(R.id.friend_status_image);
        ImageView friend_status_img =(ImageView)findViewById(R.id.friend_status_img);
        ImageView user_status_img =(ImageView)findViewById(R.id.user_status_img);

        Intent intent = getIntent();
        userFriend = (TblFriends) intent.getSerializableExtra("userFriend");
        friend_name.setText(userFriend.getFriend().getUserName()+"'s  status");
        user_status_id.setText("Only "+userFriend.getFriend().getUserName()+" can see your status.");
        String friend_status = userFriend.getUserStatus().getFriendStatus().getName();
        String user_status = userFriend.getUserStatus().getStatus().getName();
        String user_status_img_url = userFriend.getUserStatus().getStatus().getImage();
        String friend_status_img_url = userFriend.getUserStatus().getFriendStatus().getImage();

        // Friend Status part
       if(friend_status_img_url.equals(null)){
           friend_status_img.setImageResource(R.drawable.empty_face);
           friend_status_id.setText(userFriend.getFriend().getUserName()+" hasn't updated yet.");
       }else {
           Resources res = getResources();
           int resourceId = res.getIdentifier(
           friend_status_img_url , "drawable", getPackageName() );
           friend_status_img.setImageResource(resourceId);
           friend_status_id.setText(friend_status);
       }

        // User Status part
        if(user_status_img_url.equals(null)){
            user_status_img.setImageResource(R.drawable.empty_face);
            friend_status_id.setText(userFriend.getFriend().getUserName()+" hasn't updated yet.");
        }else {
            Resources res = getResources();
            int resourceId = res.getIdentifier(
                    user_status_img_url , "drawable", getPackageName() );
            user_status_img.setImageResource(resourceId);
            user_status_id.setText(user_status);
        }




        // dialog box create
     //   empty_status = findViewById(R.id.user_empty_status);
        user_status_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openDialog();
            }
        });
    }
    public void openDialog(){
    SelectorDialog selectorDialog = new SelectorDialog(this.userFriend);
    selectorDialog.show(getSupportFragmentManager(),"selectorDialog");
    }



}