package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.adarshsardarshahar.Models.UrlSolution;
import com.winbee.adarshsardarshahar.R;

import java.util.ArrayList;

public class AdsAskedSolutionAdapter extends RecyclerView.Adapter<AdsAskedSolutionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UrlSolution> list;

    public AdsAskedSolutionAdapter(Context context, ArrayList<UrlSolution> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asksolutionadapterdocument,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List

//        if (list.get(position).getType().equalsIgnoreCase("Asked")) {
//            holder.text_question.setText(list.get(position).getQuestion());
//            holder.text_date.setText(list.get(position).getDATE());
//            holder.text_user.setText(list.get(position).getUser());
//
//        }else if(list.get(position).getType().equalsIgnoreCase("Solution")){
            holder.text_solution.setText(list.get(position).getQuestion());
          //  holder.text_date_solution.setText(list.get(position).getDATE());
            holder.text_user_solution.setText(list.get(position).getUser());
//
//        }
//


    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_ask_title,text_user,text_date,text_solution,text_user_solution,text_date_solution;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ask_title = itemView.findViewById(R.id.txt_ask_title);
            text_user = itemView.findViewById(R.id.text_user);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}
