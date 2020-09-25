package com.tech41.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech41.app.Model.TblNotifications;
import com.tech41.app.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationAdapterVH> {

    private List<TblNotifications> notificationsList;
    private Context context;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    public NotificationAdapter() {
    }

    public void setData(List<TblNotifications> notificationsList) {
        this.notificationsList= notificationsList;
        notifyDataSetChanged();
    }

    public class NotificationAdapterVH extends RecyclerView.ViewHolder {

        TextView username,notification_txt,time_text;
        RelativeLayout Notification_Layout;

        public NotificationAdapterVH(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            notification_txt = itemView.findViewById(R.id.notification_txt);
            time_text = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }}

    @NonNull
    @Override
    public NotificationAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        NotificationAdapterVH evh = new NotificationAdapterVH(v,mlistener);
        return evh;

        //context = parent.getContext();
       // return new NotificationAdapter.NotificationAdapterVH(LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationAdapterVH holder, int position) {
        TblNotifications tblNotifications = notificationsList.get(position);
        String username = tblNotifications.getFriend().getUserName();
        String notification = tblNotifications.getNotification();
        String time = tblNotifications.getTime();
        int color = tblNotifications.getBgcolor();

        holder.username.setText(username);
        holder.notification_txt.setText(notification);

        if(color==1){
            holder.time_text.setText("click");
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }


}
