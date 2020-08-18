package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Activity.AskedSolutionActivity;
import com.winbee.adarshsardarshahar.Models.UrlQuestion;
import com.winbee.adarshsardarshahar.R;

import java.util.ArrayList;

public class AdsAskedQuestionAdapter extends RecyclerView.Adapter<AdsAskedQuestionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UrlQuestion> list;

    public AdsAskedQuestionAdapter(Context context, ArrayList<UrlQuestion> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdsAskedQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.askedquestionadapter,parent, false);
        return  new AdsAskedQuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsAskedQuestionAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List
      //  holder.text_question.setText(list.get(position).getFile_name_to_show());


            holder.branch_live.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, AskedSolutionActivity.class);
                    bundle.putSerializable("file_name",list.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
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
        private TextView text_question;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
       //     text_question = itemView.findViewById(R.id.text_question);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}


