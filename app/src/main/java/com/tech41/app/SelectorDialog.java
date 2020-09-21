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

import static com.tech41.app.JWT.TokenManager.userId;
import static com.tech41.app.LoginActivity.token;

public class SelectorDialog extends AppCompatDialogFragment {

    private Context context;
    private CarouselPicker imageCarousel;
    private TextView tvSelectedItem;
    private ImageView user_status_img;
    private Button btn_request_send;
    SharedPreferences preferences;
    private TblFriends userfriend;
    Timer timer;

    public SelectorDialog( TblFriends userfriend) {
         this.userfriend=userfriend;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.emoji_selector_dialog,null);

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

                        statusUpdate();

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
                tvSelectedItem.setText(""+position);
                String selected_icon;
                switch(position) {
                    case 0:
                        selected_icon="emoji_happy";
                        break;
                    case 1:
                        selected_icon="emoji_crying";
                        break;
                    case 2:
                        selected_icon="emoji_angry";
                        break;
                    case 3:
                        selected_icon="emoji_confused";
                        break;
                    case 4:
                        selected_icon="emoji_in_love";
                        break;
                    default:
                        selected_icon="empty_face";
                        break;
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

    private void statusUpdate(){
        userStatusUpdate userStatusUpdate = new userStatusUpdate(
                userfriend.getUserStatus().getUserStatusId(),
                userId, userfriend.getFriendId(),
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
