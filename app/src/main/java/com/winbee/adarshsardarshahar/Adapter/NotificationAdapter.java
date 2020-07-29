package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Activity.AdsYouTubeLiveActivity;
import com.winbee.adarshsardarshahar.Models.LiveClass;
import com.winbee.adarshsardarshahar.Models.NotificationModel;
import com.winbee.adarshsardarshahar.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NotificationModel> list;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter,parent, false);
        return  new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.text_message.setText(list.get(position).getMessage());
      //  holder.text_sender.setText(list.get(position).getSender());
        holder.text_priority.setText(list.get(position).getPriority());
        holder.text_type.setText(list.get(position).getType());
        holder.text_class.setText(list.get(position).getBucket());
        holder.text_user_date.setText(list.get(position).getDate());
        if (list.get(position).getPriority().equalsIgnoreCase("High")){
            holder.img_priority_high.setVisibility(View.VISIBLE);
            holder.img_priority_medium.setVisibility(View.GONE);
            holder.img_priority_low.setVisibility(View.GONE);
        }else if (list.get(position).getPriority().equalsIgnoreCase("Medium")){
            holder.img_priority_high.setVisibility(View.GONE);
            holder.img_priority_medium.setVisibility(View.VISIBLE);
            holder.img_priority_low.setVisibility(View.GONE);
        }else if (list.get(position).getPriority().equalsIgnoreCase("Low")){
            holder.img_priority_high.setVisibility(View.GONE);
            holder.img_priority_medium.setVisibility(View.GONE);
            holder.img_priority_low.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_message,text_sender,text_priority,text_type,text_class,text_user_date;
        private ImageView img_priority_high,img_priority_medium,img_priority_low;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_message = itemView.findViewById(R.id.text_message);
            text_sender = itemView.findViewById(R.id.text_sender);
            text_priority = itemView.findViewById(R.id.text_priority);
            text_type = itemView.findViewById(R.id.text_type);
            text_class = itemView.findViewById(R.id.text_class);
            text_user_date = itemView.findViewById(R.id.text_user_date);
            img_priority_high = itemView.findViewById(R.id.img_priority_high);
            img_priority_medium = itemView.findViewById(R.id.img_priority_medium);
            img_priority_low = itemView.findViewById(R.id.img_priority_low);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}

