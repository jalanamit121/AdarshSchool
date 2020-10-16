package com.winbee.adarshsardarshahar.Adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.adarshsardarshahar.Activity.TestOnlineActivity;
import com.winbee.adarshsardarshahar.Models.SIADDQuestionDataModel;
import com.winbee.adarshsardarshahar.Models.SIADDSolutionDataModel;
import com.winbee.adarshsardarshahar.Models.StudentQAModel;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.OnlineTestData;

import java.util.ArrayList;
import java.util.List;

public class TestQuestionAdapter extends RecyclerView.Adapter<TestQuestionAdapter.ViewHolder> {
    private Context context;
    private List<SIADDQuestionDataModel> list;

    public TestQuestionAdapter(Context context, List<SIADDQuestionDataModel> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test_question_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //setting data toAd apter List
      holder.text_title.setText(Html.fromHtml(list.get(position).getQuestionTitle()));
      holder.questionOtion1.setText(Html.fromHtml(list.get(position).getOption1()));
      holder.questionOtion1_selected.setText(Html.fromHtml(list.get(position).getOption1()));
      holder.questionOtion2.setText(Html.fromHtml(list.get(position).getOption2()));
      holder.questionOtion2_selected.setText(Html.fromHtml(list.get(position).getOption2()));
      holder.questionOtion3.setText(Html.fromHtml(list.get(position).getOption3()));
      holder.questionOtion3_selected.setText(Html.fromHtml(list.get(position).getOption3()));
      holder.questionOtion4.setText(Html.fromHtml(list.get(position).getOption4()));
      holder.questionOtion4_selected.setText(Html.fromHtml(list.get(position).getOption4()));
      holder.tv_question_num.setText(Integer.toString(position+1));




        //condition for question having image or not
        if (list.get(position).getQuestionTitle_img().endsWith("jpg")){
            holder.img_question_title.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getQuestionTitle_img()).into(holder.img_question_title);
        }else {
            holder.img_question_title.setVisibility(View.GONE);
        }
        if (list.get(position).getOption1_img().endsWith("jpg")){
            holder.img_question_option1.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption1_img()).into(holder.img_question_option1);
        }else {
            holder.img_question_option1.setVisibility(View.GONE);
        }

        if (list.get(position).getOption2_img().endsWith("jpg")){
            holder.img_question_option2.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption2_img()).into(holder.img_question_option2);
        }else {
            holder.img_question_option2.setVisibility(View.GONE);
        }
        if (list.get(position).getOption3_img().endsWith("jpg")){
            holder.img_question_option3.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption3_img()).into(holder.img_question_option3);
        }else {
            holder.img_question_option3.setVisibility(View.GONE);
        }
        if (list.get(position).getOption4_img().endsWith("jpg")){
            holder.img_question_option4.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption4_img()).into(holder.img_question_option4);
        }else {
            holder.img_question_option4.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text_title, questionOtion1, questionOtion2, questionOtion3, questionOtion4,
                questionOtion1_selected, questionOtion2_selected, questionOtion3_selected, questionOtion4_selected,
                tv_question_num,tv_review_question,tv_review_question_selected;
        private RelativeLayout layout_img1, layout_img2, layout_img3, layout_img4;
        private RelativeLayout layout_question_option1, layout_question_option2, layout_question_option3, layout_question_option4;
        private ImageView img_question_title, img_question_option1, img_question_option2, img_question_option3, img_question_option4,
                img_solution_discription;
        private String selectedAns = "";
        String ansStatus;
        String ansStatusCode;
        private int currentQuestion = 0, totalQuestion = 0, ansSelected = 0, questionReview = 0;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            tv_review_question = itemView.findViewById(R.id.tv_review_question);
            tv_review_question_selected = itemView.findViewById(R.id.tv_review_question_selected);
            img_question_title = itemView.findViewById(R.id.img_question_title);
            questionOtion1 = itemView.findViewById(R.id.questionOtion1);
            img_question_option1 = itemView.findViewById(R.id.img_question_option1);
            questionOtion2 = itemView.findViewById(R.id.questionOtion2);
            img_question_option2 = itemView.findViewById(R.id.img_question_option2);
            questionOtion3 = itemView.findViewById(R.id.questionOtion3);
            img_question_option3 = itemView.findViewById(R.id.img_question_option3);
            questionOtion4 = itemView.findViewById(R.id.questionOtion4);
            img_question_option4 = itemView.findViewById(R.id.img_question_option4);
            questionOtion1_selected = itemView.findViewById(R.id.questionOtion1_selected);
            questionOtion2_selected = itemView.findViewById(R.id.questionOtion2_selected);
            questionOtion3_selected = itemView.findViewById(R.id.questionOtion3_selected);
            questionOtion4_selected = itemView.findViewById(R.id.questionOtion4_selected);
            layout_question_option1 = itemView.findViewById(R.id.layout_question_option1);
            layout_question_option2 = itemView.findViewById(R.id.layout_question_option2);
            layout_question_option3 = itemView.findViewById(R.id.layout_question_option3);
            layout_question_option4 = itemView.findViewById(R.id.layout_question_option4);
            layout_img1 = itemView.findViewById(R.id.layout_img1);
            layout_img2 = itemView.findViewById(R.id.layout_img2);
            layout_img3 = itemView.findViewById(R.id.layout_img3);
            layout_img4 = itemView.findViewById(R.id.layout_img4);
            tv_question_num = itemView.findViewById(R.id.tv_question_num);
            questionOtion1.setOnClickListener((View.OnClickListener) this);
            questionOtion2.setOnClickListener((View.OnClickListener) this);
            questionOtion3.setOnClickListener((View.OnClickListener) this);
            questionOtion4.setOnClickListener((View.OnClickListener) this);
            questionOtion1_selected.setOnClickListener((View.OnClickListener) this);
            questionOtion2_selected.setOnClickListener((View.OnClickListener) this);
            questionOtion3_selected.setOnClickListener((View.OnClickListener) this);
            questionOtion4_selected.setOnClickListener((View.OnClickListener) this);
            tv_review_question.setOnClickListener((View.OnClickListener) this);
            tv_review_question_selected.setOnClickListener((View.OnClickListener) this);


        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.questionOtion1:
                   // v.setSelected(true);
                   // ansSelected = 1;
                    selectedAns = "1";
                    questionOtion1.setVisibility(View.GONE);
                    questionOtion2.setVisibility(View.VISIBLE);
                    questionOtion3.setVisibility(View.VISIBLE);
                    questionOtion4.setVisibility(View.VISIBLE);
                    questionOtion1_selected.setVisibility(View.VISIBLE);
                    questionOtion2_selected.setVisibility(View.GONE);
                    questionOtion3_selected.setVisibility(View.GONE);
                    questionOtion4_selected.setVisibility(View.GONE);

                    Log.i("tag", "onClick: "+getAdapterPosition());
                  //  if (ansSelected==1) {
                        String ansStatus = "answered";
                        String ansStatusCode = "1";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);
                   // }
                    break;
                case R.id.questionOtion2:
                   // v.setSelected(true);
                   // ansSelected = 1;
                    selectedAns = "2";
                    questionOtion2.setVisibility(View.GONE);
                    questionOtion1.setVisibility(View.VISIBLE);
                    questionOtion3.setVisibility(View.VISIBLE);
                    questionOtion4.setVisibility(View.VISIBLE);
                    questionOtion2_selected.setVisibility(View.VISIBLE);
                    questionOtion1_selected.setVisibility(View.GONE);
                    questionOtion3_selected.setVisibility(View.GONE);
                    questionOtion4_selected.setVisibility(View.GONE);
                    Log.i("tag", "onClick: "+getAdapterPosition());
                   // if (ansSelected==1) {
                         ansStatus = "answered";
                         ansStatusCode = "1";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);

                   // }
                    break;
                case R.id.questionOtion3:
                  //  v.setSelected(true);
                  //  ansSelected = 1;
                    selectedAns = "3";
                    questionOtion3.setVisibility(View.GONE);
                    questionOtion2.setVisibility(View.VISIBLE);
                    questionOtion1.setVisibility(View.VISIBLE);
                    questionOtion4.setVisibility(View.VISIBLE);
                    questionOtion3_selected.setVisibility(View.VISIBLE);
                    questionOtion2_selected.setVisibility(View.GONE);
                    questionOtion1_selected.setVisibility(View.GONE);
                    questionOtion4_selected.setVisibility(View.GONE);
                    Log.i("tag", "onClick: "+getAdapterPosition());
                   // if (ansSelected==1) {
                        ansStatus = "answered";
                         ansStatusCode = "1";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);
                   // }//
                    break;
                case R.id.questionOtion4:
                   // v.setSelected(true);
                  //  ansSelected = 1;
                    selectedAns = "4";
                    questionOtion4.setVisibility(View.GONE);
                    questionOtion2.setVisibility(View.VISIBLE);
                    questionOtion3.setVisibility(View.VISIBLE);
                    questionOtion1.setVisibility(View.VISIBLE);
                    questionOtion4_selected.setVisibility(View.VISIBLE);
                    questionOtion2_selected.setVisibility(View.GONE);
                    questionOtion3_selected.setVisibility(View.GONE);
                    questionOtion1_selected.setVisibility(View.GONE);
                    Log.i("tag", "onClick: "+getAdapterPosition());
                   // if (ansSelected==1) {
                        ansStatus = "answered";
                        ansStatusCode = "1";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);
                   // }
                    break;
                case R.id.questionOtion1_selected:
                    //v.setSelected(true);
                   // ansSelected = 1;
                    selectedAns = "";
                    questionOtion1.setVisibility(View.VISIBLE);
                    questionOtion1_selected.setVisibility(View.GONE);
                   // if (ansSelected==1) {
                         ansStatus = "not_answered";
                         ansStatusCode = "";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);
                   // }
                    break;
                case R.id.questionOtion2_selected:
                  //  v.setSelected(true);
                    //ansSelected = 1;
                    selectedAns = "";
                    questionOtion2.setVisibility(View.VISIBLE);
                    questionOtion2_selected.setVisibility(View.GONE);
                   // if (ansSelected==1) {
                        ansStatus = "not_answered";
                        ansStatusCode = "";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);
                  //  }
                    break;
                case R.id.questionOtion3_selected:
                 //   v.setSelected(true);
                   // ansSelected = 1;
                    selectedAns = "";
                    questionOtion3.setVisibility(View.VISIBLE);
                    questionOtion3_selected.setVisibility(View.GONE);
                   // if (ansSelected==1) {
                        ansStatus = "not_answered";
                        ansStatusCode = "";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);
                    //}
                    break;
                case R.id.questionOtion4_selected:
                  //  v.setSelected(true);
                  //  ansSelected = 1;
                    selectedAns = "";
                    questionOtion4.setVisibility(View.VISIBLE);
                    questionOtion4_selected.setVisibility(View.GONE);
                   // if (ansSelected==1) {
                        ansStatus = "not_answered";
                        ansStatusCode = "";
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus(ansStatus);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setSelectedAns(selectedAns);
                        TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode(ansStatusCode);
                   // }
                    break;
                case R.id.tv_review_question:
                   // v.setSelected(true);
                    tv_review_question_selected.setVisibility(View.VISIBLE);
                    tv_review_question.setVisibility(View.GONE);
                    TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus("review");
                    TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode("3");
                    break;
                case R.id.tv_review_question_selected:
                   // v.setSelected(true);
                    tv_review_question_selected.setVisibility(View.GONE);
                    tv_review_question.setVisibility(View.VISIBLE);
                    TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatus("not_answered");
                    TestOnlineActivity.studentQAModelList.get(getAdapterPosition()).setAnsStatusCode("");
                    break;
            }


        }
    }


}

