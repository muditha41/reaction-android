package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.tech41.app.Adapter.FriendsAdapter;
import com.tech41.app.Adapter.RequestsAdapter;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

Toolbar toolbar;
RecyclerView recyclerView;
RequestsAdapter requestsAdapter;
SharedPreferences preferences;
CarouselPicker imageCarousel;
TextView tvSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //  emoji selector
        imageCarousel = findViewById(R.id.imageCarousel);
        tvSelected = findViewById(R.id.tvSelectedItem);

//        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
//        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_happy));
//        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_confused));
//        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_in_love));
//       imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_angry));
//        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_crying));
//        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_smart));
//        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_smart));
//        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.emoji_wink));
//
//        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(this, imageItems, 0);
//        imageCarousel.setAdapter(imageAdapter);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        requestsAdapter = new RequestsAdapter();

        getRequests();

    }

    public void getRequests()
    {
        preferences = getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
        String token = preferences.getString("keyname","");
        String name = preferences.getString("name","");
        String id = preferences.getString("id","");

        Api api = RetrofitClient.getInstance().create(Api.class);

        Call<List<TblRequests>> call = api.getRequestsList("Bearer "+token,id);
        call.enqueue(new Callback<List<TblRequests>>() {
            @Override
            public void onResponse(Call<List<TblRequests>> call, Response<List<TblRequests>> response) {
                if (response.isSuccessful())
                {
                    List<TblRequests> tblRequests = response.body();
                    requestsAdapter.setData(tblRequests);
                    recyclerView.setAdapter(requestsAdapter);

                    //    Log.e("success",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<TblRequests>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }

        });
    }
}