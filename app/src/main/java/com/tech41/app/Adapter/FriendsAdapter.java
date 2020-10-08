package com.tech41.app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.tech41.app.Model.Invitation;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.StatusIntent;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.user;
import com.tech41.app.Model.userStatus;
import com.tech41.app.Model.userStatusUpdate;
import com.tech41.app.MyAccountActivity;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.ImageManager;
import com.tech41.app.Remote.RetrofitClient;
import com.tech41.app.StatusActivity;

import org.json.JSONObject;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendAdapterVH>{

    private List<TblFriends> friendsResposeList;
    private Context context;
    SharedPreferences preferences;
    private List<user>userdata;

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
        String second_text = tblFriends.getUserStatus().getStatusState();
        String time_text = tblFriends.getUserStatus().getTime();
        tblFriends.getFriend().convertToByte();
        //image decorde
        if(tblFriends.getFriend().getImageByte()!=null) {
            byte[] decoded = tblFriends.getFriend().getImageByte();
            Bitmap bitmap = BitmapFactory.decodeByteArray(decoded , 0, decoded .length);
            holder.profile_image.setImageBitmap(bitmap);
        }else {
            holder.profile_image.setImageResource(R.drawable.ic_launcher_round);
        }

        holder.username.setText(username);
        holder.time_text.setText(time_text);
      //  holder.second_text.setText(second_text);


        // message status and icons
        if(second_text != null) {
            if (second_text.equals("New Status")) {
                holder.second_text.setText(second_text);
                holder.new_satatus_icon.setVisibility(View.VISIBLE);
                holder.time_text.setTextColor(Color.parseColor("#9760C6"));
                holder.second_text.setCompoundDrawables(null, null, null, null);
            } else if (second_text.equals("Replied")) {
                holder.second_text.setText(second_text);
                holder.new_satatus_icon.setVisibility(View.GONE);
            } else {
                holder.second_text.setText(second_text);
                holder.second_text.setCompoundDrawables(null, null, null, null);
                holder.new_satatus_icon.setVisibility(View.GONE);
            }
        }else {
            holder.second_text.setVisibility(View.GONE);
            holder.second_text.setCompoundDrawables(null, null, null, null);
            holder.second_text.setText("New Friend");
            holder.new_satatus_icon.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StatusIntent statusIntent = new StatusIntent(
                tblFriends.getFriendId(),
                tblFriends.getUserId(),
                tblFriends.getUser().getUserName(),
                tblFriends.getUser().getImageByte(),
                tblFriends.getFriend().getUserName(),
                tblFriends.getFriend().getImageByte(),
                tblFriends.getFriend().getFullName(),
                tblFriends.getFriend().getDescription(),
                tblFriends.getFriend().getLocation(),
                tblFriends.getFriend().getWorkPlace(),
                tblFriends.getFriend().getRelationshipStatus(),
                tblFriends.getInviteStatus(),
                tblFriends.getUserStatus()
                );

                  Intent intent = new Intent(context, StatusActivity.class);
                  intent.putExtra("userFriend", statusIntent);
                    context.startActivity(intent);

                //status state check
                preferences = context.getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
                String token = preferences.getString("keyname","");
                userStatusUpdate userStatusUpdate = new userStatusUpdate(
                        tblFriends.getUserStatus().getUserStatusId(),
                        tblFriends.getUserId(),
                        tblFriends.getFriendId(),
                        tblFriends.getUserStatus().getStatusId());

                Api api = RetrofitClient.getInstance().create(Api.class);
                Call<ResponseError> call = api.statusChecked("Bearer "+token,userStatusUpdate);
                call.enqueue(new Callback<ResponseError>() {
                    @Override
                    public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                        if (response.isSuccessful()) {
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

    }

    @Override
    public int getItemCount() {
        return friendsResposeList.size();
    }

    public class FriendAdapterVH extends RecyclerView.ViewHolder {

        TextView username,second_text,time_text;
        CircleImageView profile_image;
        ImageView new_satatus_icon;

        public FriendAdapterVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            second_text = itemView.findViewById(R.id.second_text);
            time_text = itemView.findViewById(R.id.time_text);
            profile_image = itemView.findViewById(R.id.profile_image);
            new_satatus_icon =itemView.findViewById(R.id.new_satatus_icon);
        }



    }

}
