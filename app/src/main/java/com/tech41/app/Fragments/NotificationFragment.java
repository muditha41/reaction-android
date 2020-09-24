package com.tech41.app.Fragments;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.MailTo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech41.app.Adapter.FriendsAdapter;
import com.tech41.app.Adapter.NotificationAdapter;
import com.tech41.app.JWT.TokenManager;
import com.tech41.app.MainActivity;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.NotificationCounter;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;
import com.tech41.app.StatusActivity;

import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;


public class NotificationFragment extends Fragment {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    TextView error_txt,notificationNumber;
    NotificationAdapter notificationAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    int count = 0;
    private Context context;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_notification,container,false);
        error_txt = view.findViewById(R.id.error_txt);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotification();
                notificationAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        notificationAdapter = new NotificationAdapter();

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
                    String count = String.valueOf(tblNotifications.size()); // notification count

                }

                else
                    try {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        error_txt.setText(obj.getString("Error"));
                        error_txt.setVisibility(View.VISIBLE);

                    } catch (Exception e) { }

            }
            @Override
            public void onFailure(Call<List<TblNotifications>> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

}