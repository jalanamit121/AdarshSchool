package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.adarshsardarshahar.Activity.SubmittedWebActivity;
import com.winbee.adarshsardarshahar.Models.SubmittedDatum;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;

import java.util.List;

public class SubmittedAdapter extends RecyclerView.Adapter<SubmittedAdapter.ViewHolder> {
    private Context context;
    private List<SubmittedDatum> assignmentDatumList;

    public SubmittedAdapter(Context context, List<SubmittedDatum> assignmentDatumList){
        this.context = context;
        this.assignmentDatumList = assignmentDatumList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.submitted_assignment_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        final SubmittedDatum submittedAdapter = assignmentDatumList.get(position);
        //holder.assignment_subject.setText(assignmentDatumList.get(position).getDescription());
       holder.assignment_topic.setText(assignmentDatumList.get(position).getSubject());
       holder.assignment_teacher.setText(assignmentDatumList.get(position).getTopic());
    holder.assignment_dead_line.setText(assignmentDatumList.get(position).getDescription());


        holder.branch_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AssignmentData.AssignmentId=submittedAdapter.getAssignment_id();
               AssignmentData.Content=submittedAdapter.getStudentContent();
               AssignmentData.BucketId=submittedAdapter.getBucketId();
               AssignmentData.Subject=submittedAdapter.getSubject();
               AssignmentData.Topic=submittedAdapter.getTopic();
                Intent intent = new Intent(context, SubmittedWebActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return assignmentDatumList==null ? 0 : assignmentDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView assignment_subject,assignment_topic,assignment_teacher,assignment_dead_line;
        // private TextView branch_image;
        private RelativeLayout branch_live;
        RelativeLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignment_subject = itemView.findViewById(R.id.assignment_subject);
            assignment_topic = itemView.findViewById(R.id.assignment_topic);
            assignment_teacher = itemView.findViewById(R.id.assignment_teacher);
            assignment_dead_line = itemView.findViewById(R.id.assignment_dead_line);
            cardView = itemView.findViewById(R.id.branch_sem);
            branch_live=itemView.findViewById(R.id.branch_live);
        }
    }
}

