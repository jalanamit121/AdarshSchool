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

import com.winbee.adarshsardarshahar.Activity.AdsSemesterTopicActivity;
import com.winbee.adarshsardarshahar.Models.SemesterName;
import com.winbee.adarshsardarshahar.R;

import java.util.ArrayList;

public class AdsSemesterAdapter extends RecyclerView.Adapter<AdsSemesterAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SemesterName> list;

    public AdsSemesterAdapter(Context context, ArrayList<SemesterName> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_semester_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.branchname.setText(list.get(position).getBucket_Name());
        holder.total_video.setText(list.get(position).getTotal_Video());
        holder.total_document.setText(String.valueOf(list.get(position).getTotal_Document()));
        if (String.valueOf(list.get(position).getTotal_Document()).equalsIgnoreCase("0")&& list.get(position).getTotal_Video().equalsIgnoreCase("0")) {
            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("0", list.get(position));
                    Toast.makeText(view.getContext(), "Coming Soon", Toast.LENGTH_LONG).show();
                }
            });
        } else {

            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, AdsSemesterTopicActivity.class);
                    bundle.putSerializable("semester_name",list.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView branchname,total_video,total_document;
        private RelativeLayout branch_sem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            branchname = itemView.findViewById(R.id.gec_branchname);
            total_video = itemView.findViewById(R.id.total_video);
            total_document = itemView.findViewById(R.id.total_document);
            branch_sem = itemView.findViewById(R.id.branch_sem);
        }
    }
}


