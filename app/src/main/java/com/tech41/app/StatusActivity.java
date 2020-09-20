package com.tech41.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.Model.userStatusUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;
import in.goodiebag.carouselpicker.CarouselPicker;

public class StatusActivity extends AppCompatActivity {

    private List<TblRequests> requestsResposeList;
    private Context context;
    SharedPreferences preferences;
    TextView tvSelected,empty_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        TextView friend_name = (TextView)findViewById(R.id.friend_name);
        TextView friend_status_id = (TextView)findViewById(R.id.friend_status_id);
        TextView friend_empty_status = (TextView)findViewById(R.id.friend_empty_status);
        LottieAnimationView friend_status_image = (LottieAnimationView)findViewById(R.id.friend_status_image);

        Intent intent = getIntent();
        friend_name.setText(intent.getStringExtra("friendName")+"'s  status");
        String friend_status = intent.getStringExtra("friendStatus");

       if(friend_status == null){
           friend_empty_status.setVisibility(TextView.VISIBLE);
           friend_status_image.setVisibility(LottieAnimationView.GONE);
           friend_status_id.setText(intent.getStringExtra("friendName")+" hasn't updated yet.");
       }else {
           friend_empty_status.setVisibility(TextView.GONE);
           friend_status_image.setVisibility(LottieAnimationView.VISIBLE);
           friend_status_id.setText(intent.getStringExtra("friendStatus"));
       }




        // dialog box create
        empty_status = findViewById(R.id.empty_status);
        empty_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                imojiSelectorDilaog();
            }
        });
    }

    void imojiSelectorDilaog(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.imoji_selector_dialog,null);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window= alertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
    }


}