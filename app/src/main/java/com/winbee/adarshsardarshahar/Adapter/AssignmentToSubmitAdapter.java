package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Activity.AssignmentWebActivity;
import com.winbee.adarshsardarshahar.Models.AssignmentDatum;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;

import java.util.List;

public class AssignmentToSubmitAdapter extends RecyclerView.Adapter<AssignmentToSubmitAdapter.ViewHolder> {
    private Context context;
    private List<AssignmentDatum> assignmentDatumList;

    public AssignmentToSubmitAdapter(Context context, List<AssignmentDatum> assignmentDatumList){
        this.context = context;
        this.assignmentDatumList = assignmentDatumList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_to_submit_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
       final AssignmentDatum assignmentDatum = assignmentDatumList.get(position);
       holder.assignment_subject.setText(assignmentDatumList.get(position).getSubject());
        holder.assignment_topic.setText(assignmentDatumList.get(position).getTopic());
        holder.assignment_teacher.setText(assignmentDatumList.get(position).getFaculty());
        holder.assignment_dead_line.setText(assignmentDatumList.get(position).getDeadline_date());
        holder.branch_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssignmentData.AssignmentId=assignmentDatum.getAssignment_id();
                AssignmentData.ContentName=assignmentDatum.getContent_name();
                AssignmentData.BucketId=assignmentDatum.getBucketId();
                AssignmentData.Subject=assignmentDatum.getSubject();
                AssignmentData.Topic=assignmentDatum.getTopic();
                AssignmentData.Faculty=assignmentDatum.getFaculty();
                AssignmentData.DeadLine=assignmentDatum.getDeadline_date();
                Intent intent = new Intent(context, AssignmentWebActivity.class);
//                bundle.putSerializable("URL",assignmentDatumList.get(position));
//                intent.putExtras(bundle);
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

