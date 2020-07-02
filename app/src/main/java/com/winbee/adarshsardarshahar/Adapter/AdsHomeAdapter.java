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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.adarshsardarshahar.Activity.MyCourseSubjectActivity;
import com.winbee.adarshsardarshahar.Models.CourseDatum;
import com.winbee.adarshsardarshahar.R;

import java.util.List;

public class AdsHomeAdapter extends RecyclerView.Adapter<AdsHomeAdapter.ViewHolder> {
    private Context context;
     private List<CourseDatum> courseDatumList;

    public AdsHomeAdapter(Context context, List<CourseDatum> courseDatumList){
        this.context = context;
        this.courseDatumList = courseDatumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_home_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.branchname.setText(courseDatumList.get(position).getBucketName());
        holder.total_video.setText(courseDatumList.get(position).getTotal_Video());
        holder.total_document.setText(String.valueOf(courseDatumList.get(position).getTotal_Document()));
        Picasso.get().load(courseDatumList.get(position).getBucket_Image()).into(holder.branch_image);

        holder.branch_name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, MyCourseSubjectActivity.class);
                bundle.putSerializable("my_course",courseDatumList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return courseDatumList==null ? 0 : courseDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView branchname,total_video,total_document;
         private ImageView branch_image;
   // private TextView branch_image;
       private RelativeLayout branch_name1;
        RelativeLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            branchname = itemView.findViewById(R.id.gec_branchname);
            total_video = itemView.findViewById(R.id.total_video);
            total_document = itemView.findViewById(R.id.total_document);
            cardView = itemView.findViewById(R.id.branch_sem);
            branch_image=itemView.findViewById(R.id.branch_image);
            branch_name1=itemView.findViewById(R.id.branch_name1);
        }
    }
}

