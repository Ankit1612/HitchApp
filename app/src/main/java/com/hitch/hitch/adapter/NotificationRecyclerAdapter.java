package com.hitch.hitch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitch.hitch.R;
import com.hitch.hitch.model.NotificationItems;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ankit Shah on 15-Sep-17.
 */
public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.MyNotifyViewHolder> {

    private List<NotificationItems> notificationItems;
    private Context context;

    public NotificationRecyclerAdapter(Context context,List<NotificationItems> notificationItems){
        this.notificationItems = notificationItems;
        this.context = context;
    }

    @Override
    public MyNotifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card_view,null);
        return new MyNotifyViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MyNotifyViewHolder holder, int position) {
        holder.Name.setText(notificationItems.get(position).getName());
        holder.timeSpan.setText(notificationItems.get(position).getTimeSpan());
        holder.notification.setText(notificationItems.get(position).getNotification());

        Glide.with(context)
                .load(notificationItems.get(position).getProfileImage())
                .centerCrop()
                .fitCenter()
                .into(holder.ProfileImage);

        Glide.with(context)
                .load(notificationItems.get(position).getImage())
                .fitCenter()
                .into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return this.notificationItems.size();
    }

    public class MyNotifyViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;
        public TextView timeSpan;
        public TextView notification;
        public CircleImageView ProfileImage;
        public ImageView Image;

        public MyNotifyViewHolder(View itemView){
            super(itemView);

            Name = (TextView)itemView.findViewById(R.id.nName);
            timeSpan = (TextView)itemView.findViewById(R.id.nTimeSpan);
            notification = (TextView)itemView.findViewById(R.id.nNotification);
            ProfileImage = (CircleImageView)itemView.findViewById(R.id.nProfileImage);
            Image = (ImageView)itemView.findViewById(R.id.nImage);
        }


    }
}
