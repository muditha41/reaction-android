package com.tech41.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tech41.app.Fragments.RequestsFragment;
import com.tech41.app.LoginActivity;
import com.tech41.app.MainActivity;
import com.tech41.app.Model.Invitation;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.R;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;
import com.tech41.app.StatusActivity;
import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsAdapterVH> {

    private List<TblRequests> requestsResposeList;
    private Context context;
    SharedPreferences preferences;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
      void onConfirmClick(int position);
    }

    public void setOnItemClickListener(RequestsAdapter.OnItemClickListener listener){
        mlistener = listener;
    }

    public RequestsAdapter() {
    }

    public void setData(List<TblRequests> requestsResposeList) {
        this.requestsResposeList= requestsResposeList;
        notifyDataSetChanged();
    }

    public class RequestsAdapterVH extends RecyclerView.ViewHolder {

        TextView username;
        TextView friend_email;
        CircleImageView profile_image;
        Button btn_confirm,btn_delete;
        ImageView confirm_icon;

        public RequestsAdapterVH(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            friend_email = itemView.findViewById(R.id.friend_email);
            profile_image = itemView.findViewById(R.id.profile_image);
            btn_confirm = itemView.findViewById(R.id.btn_confirm);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public RequestsAdapter.RequestsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,parent,false);
        RequestsAdapter.RequestsAdapterVH evh = new RequestsAdapter.RequestsAdapterVH(v,mlistener);
        return evh;
      //  context = parent.getContext();
       // return new RequestsAdapter.FriendAdapterVH(LayoutInflater.from(context).inflate(R.layout.request_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.RequestsAdapterVH holder, int position) {

        TblRequests tblRequests = requestsResposeList.get(position);
        String username = tblRequests.getFriend().getUserName();
        String friend_email = tblRequests.getFriend().getEmail();

        holder.username.setText(username);
        holder.friend_email.setText(friend_email);

        ///////////////////////
        holder.btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = tblRequests.getFriend().getEmail().toString();
                acceptInvite(token,uId,email,position);

            }
        });

    }
    @Override
    public int getItemCount() {
        return requestsResposeList.size();
    }


    public void acceptInvite(String token,String uid,String email,int position){

        Api api = RetrofitClient.getInstance().create(Api.class);
        Invitation invitation = new Invitation(uid, email);
        Call<ResponseError> call = api.acceptInvite("Bearer "+token,invitation);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                if (response.isSuccessful()) {

                    requestsResposeList.remove(position);
                    notifyItemRemoved(position);
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

}