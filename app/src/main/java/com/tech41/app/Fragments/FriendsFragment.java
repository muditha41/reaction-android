package com.tech41.app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech41.app.Adapter.FriendsAdapter;
import com.tech41.app.JWT.TokenManager;
import com.tech41.app.MainActivity;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;

public class FriendsFragment extends Fragment {
    SharedPreferences preferences;
    RecyclerView recyclerView;
    FriendsAdapter friendsAdapter;
    int count = 0;
    SwipeRefreshLayout swipeRefreshLayout;

    public FriendsFragment(  ) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFriends();
                friendsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        friendsAdapter = new FriendsAdapter();
        content();
       getFriends();
        return view;
    }

    private void content() {
        count++;
        getFriends();
        refresh(2000);
    }

    private void  refresh(int miliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        }; handler.postDelayed(runnable, miliseconds);
    }

    public void getFriends()
    {
    Api api = RetrofitClient.getInstance().create(Api.class);

    Call<List<TblFriends>> call = api.getfriendslist("Bearer "+token, uId);
        call.enqueue(new Callback<List<TblFriends>>() {
        @Override
        public void onResponse(Call<List<TblFriends>> call, Response<List<TblFriends>> response) {

            if (response.isSuccessful())
            {
                List<TblFriends> tblFriends = response.body();
                friendsAdapter.setData(tblFriends);
                recyclerView.setAdapter(friendsAdapter);
            }
            else   Log.d("error","Your contact list is empty. Invite yor firends");

        }
        @Override
        public void onFailure(Call<List<TblFriends>> call, Throwable t) {

            Log.e("failure",t.getLocalizedMessage());
        }
    });
  }
}