package com.tech41.app.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationAdapterVH> {

    private List<TblNotifications> notificationsList;
    private Context context;
    SharedPreferences preferences;

    public NotificationAdapter() {
    }
    public void setData(List<TblNotifications> notificationsList) {
        this.notificationsList= notificationsList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NotificationAdapter.NotificationAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new NotificationAdapter.NotificationAdapterVH(LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationAdapterVH holder, int position) {
        TblNotifications tblNotifications = notificationsList.get(position);
        String username = tblNotifications.getFriend().getUserName();
        String notification = tblNotifications.getNotification();
        String time = tblNotifications.getTime();

        holder.username.setText(username);
        holder.notification_txt.setText(notification);
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class NotificationAdapterVH extends RecyclerView.ViewHolder {

        TextView username,notification_txt,time_text;

        public NotificationAdapterVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            notification_txt = itemView.findViewById(R.id.notification_txt);
        }
    }
}
