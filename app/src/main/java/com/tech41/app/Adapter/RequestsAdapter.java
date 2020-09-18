package com.tech41.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.R;
import com.tech41.app.StatusActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.FriendAdapterVH> {

    private List<TblRequests> requestsResposeList;
    private Context context;

    public RequestsAdapter() {
    }

    public void setData(List<TblRequests> requestsResposeList) {
        this.requestsResposeList= requestsResposeList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RequestsAdapter.FriendAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new RequestsAdapter.FriendAdapterVH(LayoutInflater.from(context).inflate(R.layout.request_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.FriendAdapterVH holder, int position) {

        TblRequests tblRequests = requestsResposeList.get(position);
        String username = tblRequests.getFriend().getUserName();
        //  String second_text = tblFriends.getStatus();
        //  String time_text = tblFriends.getLastUpdated();

        holder.username.setText(username);
        //   holder.secon d_text.setText(second_text);
        //   holder.time_text.setText(time_text);

        ///////////////////////
        holder.btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, StatusActivity.class);
//                intent.putExtra("friendId", tblFriends.getFriendId());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return requestsResposeList.size();
    }

    public class FriendAdapterVH extends RecyclerView.ViewHolder {

        TextView username;
        TextView friend_email;
        CircleImageView profile_image;
        Button btn_confirm,btn_delete;

        public FriendAdapterVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            friend_email = itemView.findViewById(R.id.friend_email);
            profile_image = itemView.findViewById(R.id.profile_image);
            btn_confirm = itemView.findViewById(R.id.btn_confirm);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}