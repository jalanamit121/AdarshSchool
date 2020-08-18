package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Activity.InstructionsActivity;
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
        if (siacDetailsDataModel.getIsAttempted()!=true) {
            viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(context, InstructionsActivity.class);
                    context.startActivity(intent);
                }
            });
        }else{
            viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Adarsh Sardarshahar");
                    builder.setMessage("You Have Attempted the Test");
                    builder.setCancelable(false);

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();

                        }
                    });
                    builder.setPositiveButton("View Solution", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
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

                            Intent intent  =new Intent(context, TestSolutionActivity.class);
                            context.startActivity(intent);
                            dialogInterface.dismiss();

                        }
                    });

                    builder.show();


                }
            });

        }


    }
    @Override
    public int getItemCount() {
        return siacDetailsDataModelList.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout branch_live1;
        ImageView live_image;
        TextView online_testname;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            branch_live1=itemView.findViewById(R.id.branch_live1);
            live_image=itemView.findViewById(R.id.live_image);
            online_testname=itemView.findViewById(R.id.online_testname);
        }
    }
}
