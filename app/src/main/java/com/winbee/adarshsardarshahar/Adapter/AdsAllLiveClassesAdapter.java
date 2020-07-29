package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Activity.AdsYouTubeLiveActivity;
import com.winbee.adarshsardarshahar.Models.LiveClass;
import com.winbee.adarshsardarshahar.R;

import java.util.ArrayList;

public class AdsAllLiveClassesAdapter extends RecyclerView.Adapter<AdsAllLiveClassesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LiveClass> list;

    public AdsAllLiveClassesAdapter(Context context, ArrayList<LiveClass> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdsAllLiveClassesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_all_live_classes,parent, false);
        return  new AdsAllLiveClassesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsAllLiveClassesAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.live_topic.setText(list.get(position).getTopic());
        holder.live_subject.setText(list.get(position).getSubject());
        holder.live_teacher.setText(list.get(position).getTeacher());
        holder.live_time.setText(list.get(position).getStart_Time());
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
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView live_topic,live_subject,live_teacher,live_time,video_started;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            live_topic = itemView.findViewById(R.id.live_topic);
            live_subject = itemView.findViewById(R.id.live_subject);
            live_teacher = itemView.findViewById(R.id.live_teacher);
            live_time = itemView.findViewById(R.id.live_time);
            video_started = itemView.findViewById(R.id.video_started);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}
