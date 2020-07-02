package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Activity.AdsYouTubeActivity;
import com.winbee.adarshsardarshahar.Activity.DriveVideoPlayerActivity;
import com.winbee.adarshsardarshahar.Activity.VideoWebActivity;
import com.winbee.adarshsardarshahar.Activity.WebActivity;
import com.winbee.adarshsardarshahar.Models.UrlName;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;

import java.util.List;


public class AdsSemesterTopicAdapter extends RecyclerView.Adapter<AdsSemesterTopicAdapter.ViewHolder> {
    private Context context;
    private List<UrlName> list;

    public AdsSemesterTopicAdapter(Context context, List<UrlName> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_semester_topic_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.subject.setText(list.get(position).getSubject());
        holder.topic.setText(list.get(position).getTopic());
        holder.faculty_name.setText(list.get(position).getFaculty());
        holder.faculty_designation.setText(list.get(position).getDesignation());
        holder.released_date.setText(list.get(position).getPublished());
      //  holder.document_type_text.setText(list.get(position).getType());
        if(list.get(position).getType().equalsIgnoreCase("PDF")){
            holder.document_type.setImageResource(R.drawable.ic_pdf);
            //holder.document_type.setText("PDF");

            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, WebActivity.class);
                    bundle.putSerializable("URL",list.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        else if(list.get(position).getType().equalsIgnoreCase("Video")){
           holder.document_type.setImageResource(R.drawable.ic_clapperboard);
            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AssignmentData.DocumentId=list.get(position).getDocumentId();
                    Intent intent= new Intent(context, DriveVideoPlayerActivity.class);
                    intent.putExtra("dURL",list.get(position).getURL());
                    context.startActivity(intent);
                    Log.i("ïnfo","Launching new activity");
                        }


            });

        }
        else if(list.get(position).getType().equalsIgnoreCase("PPT")){
            holder.document_type.setImageResource(R.drawable.ic_document);
            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(list.get(position).getURL()));
                    context.startActivity(intent);
                }

                //  Toast.makeText(view.getContext(), "video not supported ,plzz open web broswer", Toast.LENGTH_LONG).show();

            });
        }
        else if(list.get(position).getType().equalsIgnoreCase("YouTube")){
            holder.document_type.setImageResource(R.drawable.youtube_logo);
            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, AdsYouTubeActivity.class);
                    bundle.putSerializable("URL",list.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }

                //  Toast.makeText(view.getContext(), "video not supported ,plzz open web broswer", Toast.LENGTH_LONG).show();

            });
        }

    }



    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subject,topic,faculty_name,faculty_designation,released_date,document_type_text;
        ImageView document_type;
        private RelativeLayout branch_sem,layout_onclick;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.gec_subject);
            topic = itemView.findViewById(R.id.gec_topic);
            faculty_name = itemView.findViewById(R.id.faculty_name);
            faculty_designation = itemView.findViewById(R.id.faculty_designation);
            released_date = itemView.findViewById(R.id.released_date);
            document_type = itemView.findViewById(R.id.document_type);
          //  document_type_text = itemView.findViewById(R.id.document_type_text);

             branch_sem = itemView.findViewById(R.id.branch_sem_topic);
            layout_onclick = itemView.findViewById(R.id.layout_onclick);

        }

    }

}



