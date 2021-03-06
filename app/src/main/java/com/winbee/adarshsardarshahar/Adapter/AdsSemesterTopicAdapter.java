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
import com.winbee.adarshsardarshahar.Activity.VimeoActivity;
import com.winbee.adarshsardarshahar.Activity.WebActivity;
import com.winbee.adarshsardarshahar.Models.UrlName;
import com.winbee.adarshsardarshahar.NewModels.TopicContentArray;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;

import java.util.List;

import static android.content.ContentValues.TAG;


public class AdsSemesterTopicAdapter extends RecyclerView.Adapter<AdsSemesterTopicAdapter.ViewHolder> {
    private Context context;
    private List<TopicContentArray> list;

    public AdsSemesterTopicAdapter(Context context, List<TopicContentArray> list){
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
        holder.topic.setText(list.get(position).getTopic());
        holder.faculty_name.setText(list.get(position).getFaculty());
        holder.released_date.setText(list.get(position).getPublished());
        if(list.get(position).getType().equalsIgnoreCase("PDF")){
            holder.document_type.setImageResource(R.drawable.ic_pdf);
            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AssignmentData.PdfUrl=list.get(position).getURL();
                    AssignmentData.DocumentId=list.get(position).getDocumentId();
                    Intent intent = new Intent(context, WebActivity.class);
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
                    Log.d(TAG,"document"+AssignmentData.DocumentId);
                    Intent intent= new Intent(context, DriveVideoPlayerActivity.class);
                    intent.putExtra("dURL",list.get(position).getURL());
                    context.startActivity(intent);
                    Log.i("ïnfo","Launching new activity");
                        }


            });

        } else if(list.get(position).getType().equalsIgnoreCase("Vimeo")){
            holder.document_type.setImageResource(R.drawable.ic_clapperboard);
            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AssignmentData.VideoUrl=Integer.parseInt(list.get(position).getURL());
                    Intent intent= new Intent(context, VimeoActivity.class);
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
                    AssignmentData.YoutubeUrl=list.get(position).getURL();
                    AssignmentData.VideoSubject=list.get(position).getSubject();
                    AssignmentData.VideoTopic=list.get(position).getTopic();
                    AssignmentData.VideoFaculty=list.get(position).getFaculty();
                    AssignmentData.VideoPublish=list.get(position).getPublished();
                    Intent intent = new Intent(context, AdsYouTubeActivity.class);
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
            topic = itemView.findViewById(R.id.gec_topic);
            faculty_name = itemView.findViewById(R.id.faculty_name);
            released_date = itemView.findViewById(R.id.released_date);
            document_type = itemView.findViewById(R.id.document_type);
          //  document_type_text = itemView.findViewById(R.id.document_type_text);

             branch_sem = itemView.findViewById(R.id.branch_sem_topic);
            layout_onclick = itemView.findViewById(R.id.layout_onclick);

        }

    }

}



