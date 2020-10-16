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
import com.winbee.adarshsardarshahar.NewModels.LiveClassContentaArray;
import com.winbee.adarshsardarshahar.R;

import java.util.ArrayList;

public class AdsHomeLiveAdapter extends RecyclerView.Adapter<AdsHomeLiveAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LiveClassContentaArray> list;

    public AdsHomeLiveAdapter(Context context, ArrayList<LiveClassContentaArray> List){
        this.context = context;
        this.list = List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_home_adepter_live,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List



        holder.subjectName.setText(list.get(position).getSubject());
        holder.subjectTopic.setText(list.get(position).getTopic());
        holder.StartingTime.setText(list.get(position).getStart_Time());
        holder.video_started.setText(list.get(position).getCS_type_name());
        holder.branch_live.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(position).getCS_type_code().equals(1)||list.get(position).getCS_type_code().equals(2)) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, AdsYouTubeLiveActivity.class);
                    bundle.putSerializable("ContentLink",list.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    } else if (list.get(position).getCS_type_code().equals(0)) {
                        Toast.makeText(context, "Class Not Started yet", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error Occur,Contact Support", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : 2;
        //return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectName,subjectTopic,StartingTime,video_started;
        ImageView live_image;
        private RelativeLayout branch_live;
        RelativeLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.gec_live_subject);
            subjectTopic = itemView.findViewById(R.id.live_topic);
            StartingTime = itemView.findViewById(R.id.live_started);
            video_started = itemView.findViewById(R.id.video_started);
            cardView = itemView.findViewById(R.id.branch_sem);
            live_image=itemView.findViewById(R.id.live_image);
            branch_live=itemView.findViewById(R.id.branch_live);
        }
    }
}

