package com.tech41.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech41.app.Model.TblFriends;
import com.tech41.app.R;
import com.tech41.app.StatusActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendAdapterVH>{

    private List<TblFriends> friendsResposeList;
    private Context context;

    public FriendsAdapter() {
    }

    public void setData(List<TblFriends> friendsResposeList) {
        this.friendsResposeList= friendsResposeList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FriendAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new FriendAdapterVH(LayoutInflater.from(context).inflate(R.layout.friends_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapterVH holder, int position) {

        TblFriends tblFriends = friendsResposeList.get(position);
        String username = tblFriends.getFriend().getUserName();
      //  String second_text = tblFriends.getStatus();
      //  String time_text = tblFriends.getLastUpdated();

        holder.username.setText(username);
     //   holder.secon d_text.setText(second_text);
     //   holder.time_text.setText(time_text);

        ///////////////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StatusActivity.class);
                intent.putExtra("friendId", tblFriends.getFriendId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return friendsResposeList.size();
    }

    public class FriendAdapterVH extends RecyclerView.ViewHolder {

        TextView username;
        TextView second_text;
        TextView time_text;
        CircleImageView profile_image;

        public FriendAdapterVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            second_text = itemView.findViewById(R.id.second_text);
            time_text = itemView.findViewById(R.id.time_text);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}
