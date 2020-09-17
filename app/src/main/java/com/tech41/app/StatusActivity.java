package com.tech41.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;
import in.goodiebag.carouselpicker.CarouselPicker;

public class StatusActivity extends AppCompatActivity {

    View user_status;
    TextView empty_status;
    ImageView txt_close;
    CarouselPicker imageCarousel;
    TextView tvSelected;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

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

        //  emoji selector

        imageCarousel = findViewById(R.id.imageCarousel);
        tvSelected = findViewById(R.id.tvSelectedItem);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.imoji_selector_dialog,null);

        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_happy));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_confused));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_in_love));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_angry));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_crying));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_smart));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_smart));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_wink));

     //   CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(this, imageItems, 0);
    //    imageCarousel.setAdapter(imageAdapter);


       // Button btn_Invite = view.findViewById(R.id.btn_request_send);

//        btn_Invite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                layout_success.setVisibility(view.VISIBLE);
//
//            }
//        });

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