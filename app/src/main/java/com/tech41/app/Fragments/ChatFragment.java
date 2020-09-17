package com.tech41.app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech41.app.Adapter.UserAdapter;
import com.tech41.app.Model.Chat;
import com.tech41.app.Model.user;
import com.tech41.app.R;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<user> muser;

    FirebaseUser fuser;
    DatabaseReference reference;

    private  List<String> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_chat, container, false);

       recyclerView = view.findViewById(R.id.recycler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       fuser = FirebaseAuth.getInstance().getCurrentUser();

       usersList = new ArrayList<>();
       reference = FirebaseDatabase.getInstance().getReference("Chats");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               usersList.clear();

               for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                   Chat chat = snapshot.getValue(Chat.class);

                   if(chat.getSender().equals(fuser.getUid())){
                       usersList.add(chat.getReceiver());
                   }
                   if (chat.getReceiver().equals(fuser.getUid())){
                       usersList.add(chat.getSender());
                   }
               }

               readChats();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        return view;
    }

    private void readChats(){
        muser = new ArrayList<>();

        reference =  FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                muser.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user user = snapshot.getValue(com.tech41.app.Model.user.class);

                    for (String id: usersList){
                        if (user.getId().equals(id)){
                            if (muser.size() !=0){
                                for (com.tech41.app.Model.user userl :muser){
                                    if(!user.getId().equals(userl.getId())){
                                        muser.add(user);
                                    }
                                }
                            } else {
                                muser.add(user);
                            }
                        }
                    }
                } //userAdapter = new UserAdapter(getContext(),muser);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}