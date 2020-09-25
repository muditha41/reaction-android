package com.tech41.app.Fragments;

import android.content.Context;
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

import com.tech41.app.Adapter.RequestsAdapter;
import com.tech41.app.Model.Invitation;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;
import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestsFragment extends Fragment {

    SharedPreferences preferences;
    RecyclerView recyclerView;
    RequestsAdapter requestsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<TblRequests> requestsList;
    int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        requestsAdapter = new RequestsAdapter();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRequests();
                requestsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        content();
        getRequests();
        return view;
    }

    private void content() {
        count++;
        getRequests();
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

    public void getRequests()
    {
        preferences  = getActivity().getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
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
                else   Log.d("error","Your contact list is empty. Invite yor firends");
            }

            @Override
            public void onFailure(Call<List<TblRequests>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }
}