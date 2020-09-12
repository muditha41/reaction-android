package com.tech41.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.tech41.app.Adapter.FriendsAdapter;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

Toolbar toolbar;
RecyclerView recyclerView;
FriendsAdapter friendsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        friendsAdapter = new FriendsAdapter();

        getFriends();

    }

    public void getFriends()
    {

           //textViewResult = findViewById(R.id.text_view_result);
        Api api = RetrofitClient.getInstance().create(Api.class);

        Call<List<TblFriends>> call = api.getfriends();
        call.enqueue(new Callback<List<TblFriends>>() {
            @Override
            public void onResponse(Call<List<TblFriends>> call, Response<List<TblFriends>> response) {

                if (response.isSuccessful())
                {
                    List<TblFriends> tblFriends = response.body();
                    friendsAdapter.setData(tblFriends);
                    recyclerView.setAdapter(friendsAdapter);

                //    Log.e("success",response.body().toString());
                }

            }
            @Override
            public void onFailure(Call<List<TblFriends>> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }
}