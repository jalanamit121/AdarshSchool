package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.askedquestionadapterdocument,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.txt_user.setText(SharedPrefManager.getInstance(context).refCode().getName());
        holder.txt_ask_question.setText(list.get(position).getFile_name_to_show());
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
        private TextView txt_time,txt_user,txt_ask_title,txt_ask_question,txt_commments;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_user = itemView.findViewById(R.id.txt_user);
            txt_ask_title = itemView.findViewById(R.id.txt_ask_title);
            txt_ask_question = itemView.findViewById(R.id.txt_ask_question);
            txt_commments = itemView.findViewById(R.id.txt_commments);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}


