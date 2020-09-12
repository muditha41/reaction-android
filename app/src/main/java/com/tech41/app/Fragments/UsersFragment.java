package com.tech41.app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech41.app.Adapter.FriendsAdapter;
import com.tech41.app.Adapter.UserAdapter;
import com.tech41.app.LoginActivity;
import com.tech41.app.MainActivity;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.User;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    FriendsAdapter friendsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
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