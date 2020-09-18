package com.tech41.app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech41.app.Adapter.FriendsAdapter;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {
    SharedPreferences preferences;
    RecyclerView recyclerView;
    FriendsAdapter friendsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

      //  refreshLayout();

        friendsAdapter = new FriendsAdapter();

        getFriends();
        return view;
    }

    private void refreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFriends();
            }
        });
    }

    public void getFriends()
    {
     preferences  = getActivity().getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
   String token = preferences.getString("keyname","");
    String name = preferences.getString("name","");
    String id = preferences.getString("id","");

    Api api = RetrofitClient.getInstance().create(Api.class);

    Call<List<TblFriends>> call = api.getfriendslist("Bearer "+token,id);
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
            else   Log.d("error","Your contact list is empty. Invite yor firends");

        }
        @Override
        public void onFailure(Call<List<TblFriends>> call, Throwable t) {

            Log.e("failure",t.getLocalizedMessage());
        }
    });
  }

}