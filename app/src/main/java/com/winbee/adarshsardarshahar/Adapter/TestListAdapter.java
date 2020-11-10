package com.winbee.adarshsardarshahar.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Activity.AdsHomeActivity;
import com.winbee.adarshsardarshahar.Activity.InstructionsActivity;
import com.winbee.adarshsardarshahar.Activity.OnlineQuestionActivity;
import com.winbee.adarshsardarshahar.Activity.TestRankActivity;
import com.winbee.adarshsardarshahar.Activity.TestSolutionActivity;
import com.winbee.adarshsardarshahar.Models.SIACDetailsDataModel;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.OnlineTestData;

import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.CustomViewHolder> {
    private Context context;
    private List<SIACDetailsDataModel> siacDetailsDataModelList;

    public TestListAdapter(Context context, List<SIACDetailsDataModel> siacDetailsDataModelList) {
        this.context=context;
        this.siacDetailsDataModelList = siacDetailsDataModelList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test_list,parent,false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        final SIACDetailsDataModel siacDetailsDataModel = siacDetailsDataModelList.get(position);
        viewHolder.online_testname.setText(siacDetailsDataModel.getPaperName());
        viewHolder.total_question.setText(siacDetailsDataModel.getTotal_Number_of_question()+" Questions");
        viewHolder.total_time.setText(siacDetailsDataModel.getTime()+" Mins");
        viewHolder.txt_closed_message.setText(siacDetailsDataModel.getTest_Closed_Message());
        viewHolder.txt_closed_date.setText(siacDetailsDataModel.getTest_Closed_date());

        if (!siacDetailsDataModel.getIs_closed_Notification_On()){
            viewHolder.txt_closed_date.setVisibility(View.GONE);
            viewHolder.txt_closed_message.setVisibility(View.GONE);
        } else if (siacDetailsDataModel.getIs_closed_Notification_On()) {
            viewHolder.txt_closed_date.setVisibility(View.VISIBLE);
            viewHolder.txt_closed_message.setVisibility(View.VISIBLE);
        }


        if (siacDetailsDataModel.getIsOpen().equalsIgnoreCase("1")){
            viewHolder.branch_live1.setVisibility(View.VISIBLE);
            if (siacDetailsDataModel.getIsAttempted()){
                //true
                if (siacDetailsDataModel.getIsResultPublish()){
                    //true
                    viewHolder.layout_pervious.setVisibility(View.VISIBLE);
                    viewHolder.layout_rank.setVisibility(View.VISIBLE);
                    viewHolder.layout_start.setVisibility(View.GONE);
                    viewHolder.layout_status.setVisibility(View.GONE);
                    viewHolder.txt_notattempt.setVisibility(View.GONE);
                    viewHolder.layout_closed.setVisibility(View.GONE);
                    viewHolder.layout_new_start.setVisibility(View.GONE);
                }else if (!siacDetailsDataModel.getIsResultPublish()){
                    //false
                    viewHolder.layout_pervious.setVisibility(View.GONE);
                    viewHolder.layout_rank.setVisibility(View.GONE);
                    viewHolder.layout_start.setVisibility(View.GONE);
                    viewHolder.layout_new_start.setVisibility(View.GONE);
                    viewHolder.layout_closed.setVisibility(View.GONE);
                    viewHolder.txt_notattempt.setVisibility(View.GONE);
                    viewHolder.layout_status.setVisibility(View.VISIBLE);
                }
                if (siacDetailsDataModel.getIsPaperClosed()){
                    //true
                    if (siacDetailsDataModel.getIsResultPublish()){
                        //true
                        viewHolder.layout_pervious.setVisibility(View.VISIBLE);
                        viewHolder.layout_rank.setVisibility(View.VISIBLE);
                        viewHolder.layout_start.setVisibility(View.GONE);
                        viewHolder.layout_new_start.setVisibility(View.GONE);
                        viewHolder.layout_status.setVisibility(View.GONE);
                        viewHolder.txt_notattempt.setVisibility(View.GONE);
                        viewHolder.layout_closed.setVisibility(View.GONE);
                    }else if (!siacDetailsDataModel.getIsResultPublish()){
                        //false
                        viewHolder.layout_pervious.setVisibility(View.GONE);
                        viewHolder.layout_rank.setVisibility(View.GONE);
                        viewHolder.layout_start.setVisibility(View.GONE);
                        viewHolder.layout_new_start.setVisibility(View.GONE);
                        viewHolder.layout_status.setVisibility(View.VISIBLE);
                        viewHolder.txt_notattempt.setVisibility(View.VISIBLE);
                        viewHolder.layout_closed.setVisibility(View.GONE);
                    }

                }else if (!siacDetailsDataModel.getIsPaperClosed()){
                    //false
                    viewHolder.layout_pervious.setVisibility(View.GONE);
                    viewHolder.layout_rank.setVisibility(View.GONE);
                    viewHolder.layout_start.setVisibility(View.VISIBLE);
                    viewHolder.layout_new_start.setVisibility(View.GONE);
                    viewHolder.layout_status.setVisibility(View.GONE);
                    viewHolder.txt_notattempt.setVisibility(View.GONE);
                    viewHolder.layout_closed.setVisibility(View.GONE);
                }
            }else if (!siacDetailsDataModel.getIsAttempted()){
                //false
                if (siacDetailsDataModel.getIsResultPublish()){
                    //true
                    viewHolder.layout_pervious.setVisibility(View.VISIBLE);
                    viewHolder.layout_rank.setVisibility(View.VISIBLE);
                    viewHolder.layout_start.setVisibility(View.GONE);
                    viewHolder.layout_status.setVisibility(View.GONE);
                    viewHolder.layout_closed.setVisibility(View.GONE);
                    viewHolder.layout_new_start.setVisibility(View.GONE);
                    viewHolder.txt_notattempt.setVisibility(View.VISIBLE);
                }else if (!siacDetailsDataModel.getIsResultPublish()){
                    //false
                    viewHolder.layout_pervious.setVisibility(View.GONE);
                    viewHolder.layout_rank.setVisibility(View.GONE);
                    viewHolder.layout_start.setVisibility(View.GONE);
                    viewHolder.layout_new_start.setVisibility(View.GONE);
                    viewHolder.layout_closed.setVisibility(View.GONE);
                    viewHolder.txt_notattempt.setVisibility(View.GONE);
                    viewHolder.layout_status.setVisibility(View.VISIBLE);
                }
                if (siacDetailsDataModel.getIsPaperClosed()){
                    viewHolder.txt_notattempt.setVisibility(View.VISIBLE);
                    //true
                    if (siacDetailsDataModel.getIsResultPublish()){
                        //true
                        viewHolder.layout_pervious.setVisibility(View.VISIBLE);
                        viewHolder.txt_notattempt.setVisibility(View.VISIBLE);
                        viewHolder.layout_rank.setVisibility(View.VISIBLE);
                        viewHolder.layout_start.setVisibility(View.GONE);
                        viewHolder.layout_new_start.setVisibility(View.GONE);
                        viewHolder.layout_status.setVisibility(View.GONE);
                        viewHolder.layout_closed.setVisibility(View.GONE);
                    }else if (!siacDetailsDataModel.getIsResultPublish()){
                        //false
                        viewHolder.layout_pervious.setVisibility(View.GONE);
                        viewHolder.layout_rank.setVisibility(View.GONE);
                        viewHolder.layout_start.setVisibility(View.GONE);
                        viewHolder.layout_new_start.setVisibility(View.GONE);
                        viewHolder.layout_status.setVisibility(View.VISIBLE);
                        viewHolder.layout_closed.setVisibility(View.GONE);
                        viewHolder.txt_notattempt.setVisibility(View.GONE);
                    }

                }else if (!siacDetailsDataModel.getIsPaperClosed()){
                    //false
                    viewHolder.layout_pervious.setVisibility(View.GONE);
                    viewHolder.layout_rank.setVisibility(View.GONE);
                    viewHolder.layout_start.setVisibility(View.GONE);
                    viewHolder.layout_new_start.setVisibility(View.VISIBLE);
                    viewHolder.layout_status.setVisibility(View.GONE);
                    viewHolder.layout_closed.setVisibility(View.GONE);
                    viewHolder.txt_notattempt.setVisibility(View.GONE);
                }
            }
        }else if (siacDetailsDataModel.getIsOpen().equalsIgnoreCase("0")){
            viewHolder.branch_live1.setVisibility(View.GONE);
        }

       viewHolder.layout_status.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Dialog dialog = new Dialog(context);
               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               dialog.setContentView(R.layout.custom_payment_alert);
               RelativeLayout layout_home = dialog.findViewById(R.id.layout_home);
               TextView txt_course = dialog.findViewById(R.id.txt_course);
               txt_course.setText(siacDetailsDataModel.getResultPublishMessage());
               layout_home.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(context, AdsHomeActivity.class);
                       context.startActivity(intent);
                   }
               });

               dialog.show();
               dialog.setCancelable(false);
           }
       });




            viewHolder.layout_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnlineTestData.bucketIDre = siacDetailsDataModel.getBucketID();
                    OnlineTestData.totalQuestion = siacDetailsDataModel.getTotal_Number_of_question();
                    OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                    OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                    OnlineTestData.paperSection_Encode = siacDetailsDataModel.getPaperSection_Encode();
                    OnlineTestData.isNegativeMarking_encode = siacDetailsDataModel.getIsNegativeMarking_encode();
                    OnlineTestData.time = siacDetailsDataModel.getTime();
                    OnlineTestData.isOpen = siacDetailsDataModel.getIsOpen();
                    OnlineTestData.openDate = siacDetailsDataModel.getOpenDate();
                    OnlineTestData.isNegativeMarking_decode = siacDetailsDataModel.getIsNegativeMarking_decode();
                    OnlineTestData.isPremium_encode = siacDetailsDataModel.getIsPremium_encode();
                    OnlineTestData.isPremium_decode = siacDetailsDataModel.getIsPremium_decode();
                    OnlineTestData.description = siacDetailsDataModel.getDescription();
                    OnlineTestData.resultMessage=siacDetailsDataModel.getResultPublishMessage();
                    OnlineTestData.resultPublish=siacDetailsDataModel.getIsResultPublish();
                    Intent intent = new Intent(context, InstructionsActivity.class);
                    context.startActivity(intent);
                }
            });

        viewHolder.layout_new_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineTestData.bucketIDre = siacDetailsDataModel.getBucketID();
                OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                OnlineTestData.paperSection_Encode = siacDetailsDataModel.getPaperSection_Encode();
                OnlineTestData.isNegativeMarking_encode = siacDetailsDataModel.getIsNegativeMarking_encode();
                OnlineTestData.time = siacDetailsDataModel.getTime();
                OnlineTestData.isOpen = siacDetailsDataModel.getIsOpen();
                OnlineTestData.openDate = siacDetailsDataModel.getOpenDate();
                OnlineTestData.isNegativeMarking_decode = siacDetailsDataModel.getIsNegativeMarking_decode();
                OnlineTestData.isPremium_encode = siacDetailsDataModel.getIsPremium_encode();
                OnlineTestData.isPremium_decode = siacDetailsDataModel.getIsPremium_decode();
                OnlineTestData.description = siacDetailsDataModel.getDescription();
                OnlineTestData.resultMessage=siacDetailsDataModel.getResultPublishMessage();
                OnlineTestData.resultPublish=siacDetailsDataModel.getIsResultPublish();
                Intent intent = new Intent(context, InstructionsActivity.class);
                context.startActivity(intent);
            }
        });

        viewHolder.layout_pervious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineTestData.bucketIDre = siacDetailsDataModel.getBucketID();
                OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                OnlineTestData.paperSection_Encode = siacDetailsDataModel.getPaperSection_Encode();
                OnlineTestData.isNegativeMarking_encode = siacDetailsDataModel.getIsNegativeMarking_encode();
                OnlineTestData.time = siacDetailsDataModel.getTime();
                OnlineTestData.isOpen = siacDetailsDataModel.getIsOpen();
                OnlineTestData.openDate = siacDetailsDataModel.getOpenDate();
                OnlineTestData.isNegativeMarking_decode = siacDetailsDataModel.getIsNegativeMarking_decode();
                OnlineTestData.isPremium_encode = siacDetailsDataModel.getIsPremium_encode();
                OnlineTestData.isPremium_decode = siacDetailsDataModel.getIsPremium_decode();
                OnlineTestData.description = siacDetailsDataModel.getDescription();
                OnlineTestData.resultMessage=siacDetailsDataModel.getResultPublishMessage();
                OnlineTestData.resultPublish=siacDetailsDataModel.getIsResultPublish();
                Intent intent = new Intent(context, TestSolutionActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.layout_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                Intent intent = new Intent(context, TestRankActivity.class);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return siacDetailsDataModelList.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout branch_live1;
        ImageView live_image;
        LinearLayout layout_start,layout_pervious,layout_rank;
        RelativeLayout layout_new_start,layout_status,layout_closed;
        TextView online_testname,total_question,total_time,txt_closed_date,txt_closed_message,txt_notattempt;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            branch_live1=itemView.findViewById(R.id.branch_live1);
            live_image=itemView.findViewById(R.id.live_image);
            online_testname=itemView.findViewById(R.id.online_testname);
            layout_start=itemView.findViewById(R.id.layout_start);
            layout_new_start=itemView.findViewById(R.id.layout_new_start);
            layout_pervious=itemView.findViewById(R.id.layout_pervious);
            total_question=itemView.findViewById(R.id.total_question);
            total_time=itemView.findViewById(R.id.total_time);
            txt_closed_date=itemView.findViewById(R.id.txt_closed_date);
            txt_closed_message=itemView.findViewById(R.id.txt_closed_message);
            layout_rank=itemView.findViewById(R.id.layout_rank);
            layout_status=itemView.findViewById(R.id.layout_status);
            layout_closed=itemView.findViewById(R.id.layout_closed);
            txt_notattempt=itemView.findViewById(R.id.txt_notattempt);
        }
    }
}
