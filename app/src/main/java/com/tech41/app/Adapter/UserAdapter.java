package com.tech41.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.tech41.app.MessageActivity;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.User;
import com.tech41.app.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<TblFriends> friends;

    public UserAdapter(Context mContext, List<TblFriends> friends) {
        this.friends = friends;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TblFriends user = friends.get(position);
        holder.username.setText(user.getFriendId());
        if(user.getProfileImage().equals("NULL")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
;        } else{
            Glide.with(mContext).load(user.getProfileImage()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("friendId",user.getFriendId());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}


