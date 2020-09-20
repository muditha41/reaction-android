package com.tech41.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech41.app.Model.Invitation;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.userStatusUpdate;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;
import com.tech41.app.StatusActivity;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendAdapterVH>{

    private List<TblFriends> friendsResposeList;
    private Context context;
    SharedPreferences preferences;

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
        String time_text = tblFriends.getUserStatus().getFriendStatusTimeStamp();

        holder.username.setText(username);
        holder.second_text.setText(second_text);
        holder.time_text.setText(time_text);

        ///////////////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StatusActivity.class);
                intent.putExtra("friendId", tblFriends.getFriendId());
                intent.putExtra("friendName", tblFriends.getFriend().getUserName());
                intent.putExtra("friendStatus", tblFriends.getUserStatus().getFriendStatus().getName());
                intent.putExtra("friendStatusImg", tblFriends.getUserStatus().getFriendStatus().getImage());

                context.startActivity(intent);

                preferences = context.getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
                String token = preferences.getString("keyname","");
                userStatusUpdate userStatusUpdate = new userStatusUpdate(
                        tblFriends.getUserStatus().getUserStatusId(),
                        tblFriends.getUserId(),
                        tblFriends.getFriendId(),
                        tblFriends.getUserStatus().getStatusId()
                );

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
