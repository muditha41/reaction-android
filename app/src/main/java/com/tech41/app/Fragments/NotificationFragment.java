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
import android.widget.TextView;

import com.tech41.app.Adapter.NotificationAdapter;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.MyAccountActivity;
import com.tech41.app.NotificationCounter;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;


public class NotificationFragment extends Fragment {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    int count = 0;
    private Context context;
    List<TblNotifications> tblNotifications;
     public static String Ncount;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_notification,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Api api = RetrofitClient.getInstance().create(Api.class);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotification();
             //   notificationAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        notificationAdapter = new NotificationAdapter( );

        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                // API not found method error

                Api api = RetrofitClient.getInstance().create(Api.class);
                Call<ResponseError> call = api.checkNotifications("Bearer "+token,uId);
                call.enqueue(new Callback<ResponseError>() {
                    @Override
                    public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                        if (response.isSuccessful()) {
                            notificationAdapter.notifyDataSetChanged();
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
        });

        content();
        getNotification();
        return view;
    }

    private void content() {
        count++;
        getNotification();
        refresh(5000);
    }

    private void refresh(int miliseconds) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        }; handler.postDelayed(runnable, miliseconds);
    }

    private void getNotification() {

        Api api = RetrofitClient.getInstance().create(Api.class);

        Call<List<TblNotifications>> call = api.getNotification("Bearer "+token,uId);
        call.enqueue(new Callback<List<TblNotifications>>() {
            @Override
            public void onResponse(Call<List<TblNotifications>> call, Response<List<TblNotifications>> response) {

                if (response.isSuccessful())
                {
                    List<TblNotifications> tblNotifications = response.body();
                    notificationAdapter.setData(tblNotifications);
                    recyclerView.setAdapter(notificationAdapter);
                     Ncount = String.valueOf(tblNotifications.size()); // notification count
                }

                else
                    try {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        String e = (obj.getString("message").toString());

                    } catch (Exception e) { }

            }
            @Override
            public void onFailure(Call<List<TblNotifications>> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }




}