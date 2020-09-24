package com.tech41.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.tech41.app.Fragments.FriendsFragment;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.userStatusUpdate;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import in.goodiebag.carouselpicker.CarouselPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech41.app.MainActivity.token;

public class SelectorDialog extends AppCompatDialogFragment {

    private Context context;
    private CarouselPicker imageCarousel;
    private TextView tvSelectedItem;
    private ImageView user_status_img;
    private Button btn_request_send;
    SharedPreferences preferences;
    private TblFriends userfriend;
    private static String selected_icon;
    Timer timer;
    private SelectorDialogListner listner;

    public SelectorDialog( TblFriends userfriend) {
         this.userfriend=userfriend;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.emoji_selector_dialog,null);

        preferences  = getActivity().getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
        String token = preferences.getString("keyname","");
        String id = preferences.getString("id","");

        imageCarousel = view.findViewById(R.id.imageCarousel);
        tvSelectedItem = view.findViewById(R.id.tvSelectedItem);
        user_status_img = view.findViewById(R.id.user_status_img);
        btn_request_send=(Button) view.findViewById(R.id.btn_request_send);

        builder.setView(view)
                .setTitle("")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        statusUpdate(id);
                        listner.applyTexts(selected_icon);
                    }
                });

        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_happy));//0
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_crying));//1
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_angry));//2
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_confused));//3
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_in_love));//4

        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(getContext(), imageItems, 0);
        imageCarousel.setAdapter(imageAdapter);

        imageCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selected_icon = null;
                if(position==0){
                    tvSelectedItem.setText(""+2);
                    selected_icon="emoji_happy";
                }else if(position==1){
                    tvSelectedItem.setText(""+3);
                    selected_icon="emoji_crying";
                }else if(position==2) {
                    tvSelectedItem.setText("" +4);
                    selected_icon = "emoji_angry";
                }else if(position==3) {
                    tvSelectedItem.setText("" +5);
                    selected_icon = "emoji_confused";
                }else if(position==4) {
                    tvSelectedItem.setText("" +6);
                    selected_icon = "emoji_in_love";
                }else{
                    tvSelectedItem.setText("" +1);
                    selected_icon = "Empty";
                }

                Resources res = getResources();
               String packageName = "com.tech41.app";
                int resourceId = res.getIdentifier(
                       selected_icon , "drawable", packageName);
                int img = resourceId;
                user_status_img.setImageResource(resourceId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner =(SelectorDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "error in lisner part");
        }
    }

    public interface SelectorDialogListner{
        void applyTexts(String imgId);
    }

    private void statusUpdate(String id){
        userStatusUpdate userStatusUpdate = new userStatusUpdate(
                userfriend.getUserStatus().getUserStatusId(),
                id, userfriend.getFriendId(),
                Integer.parseInt(tvSelectedItem.getText().toString()));

        Api api = RetrofitClient.getInstance().create(Api.class);
        Call<ResponseError> call = api.statusUpdate("Bearer "+token,userStatusUpdate);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                if (response.isSuccessful()) {



                }
                else
                    try {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        String e = (obj.getString("message"));
                    } catch (Exception e) { }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }
}
