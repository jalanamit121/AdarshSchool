package com.winbee.adarshsardarshahar.Activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Adapter.TestQuestionAdapter;
import com.winbee.adarshsardarshahar.Fragment.BottomSheetQuestionFragment;
import com.winbee.adarshsardarshahar.Fragment.BottomSheetResultFragment;
import com.winbee.adarshsardarshahar.Models.ResultModel;
import com.winbee.adarshsardarshahar.Models.SIADDQuestionDataModel;
import com.winbee.adarshsardarshahar.Models.SIADDataModel;
import com.winbee.adarshsardarshahar.Models.SIADMainModel;
import com.winbee.adarshsardarshahar.Models.StudentQAModel;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.adarshsardarshahar.Utils.OnlineTestData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestOnlineActivity extends AppCompatActivity {

    private RecyclerView all_onlinequestion;
    TextView tv_timer,tv_testName;
    ImageView listBtn;
    private Button btn_submit_test;
    ProgressBarUtil progressBarUtil;
    private TestQuestionAdapter adapter;
    private List<SIADDQuestionDataModel> siaddQuestionDataModelList;
    public static List<StudentQAModel> studentQAModelList=new ArrayList<>();
    int milliTimer,cntMillitimer,countTimer;
    int ReHrs,ReMin,ReSec;
    CountDownTimer countDownTimer;
    String UserId;
    BottomSheetQuestionFragment bottomSheetFragment = new BottomSheetQuestionFragment();
    BottomSheetResultFragment bottomSheetResultFragment=new BottomSheetResultFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_online);
        all_onlinequestion=findViewById(R.id.all_onlinequestion);
        listBtn=findViewById(R.id.listBtn);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialogFragment();
            }
        });
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        tv_timer=findViewById(R.id.tv_timer);
        tv_testName=findViewById(R.id.tv_testName);
        tv_testName.setText(OnlineTestData.paperName);
        btn_submit_test=findViewById(R.id.btn_submit_test);
        btn_submit_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        TestOnlineActivity.this);
                alertDialogBuilder.setTitle("Exit");
                alertDialogBuilder
                        .setMessage("Do you really want to Submit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //TestOnlineActivity.this.finish();
                                //submitData();
                                showResult();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        progressBarUtil   =  new ProgressBarUtil(this);
        countTimer=Integer.parseInt(OnlineTestData.time);
        countTimer=countTimer*60;
        milliTimer=(countTimer+1)*1000;
        cntMillitimer=milliTimer;
        getQuestionData();


    }
    @Override
    public void onBackPressed(){
        // super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                TestOnlineActivity.this);
        alertDialogBuilder.setTitle("Exit");
        alertDialogBuilder
                .setMessage("Do you really want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(TestOnlineActivity.this,AdsHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void getQuestionData() {
        progressBarUtil.showProgress();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIADMainModel> call=apiClient.fetchSIADDATA(OnlineTestData.org_code,OnlineTestData.auth_code,OnlineTestData.bucketID,OnlineTestData.paperID);
        call.enqueue(new Callback<SIADMainModel>() {
            @Override
            public void onResponse(Call<SIADMainModel> call, Response<SIADMainModel> response) {
                progressBarUtil.hideProgress();
                SIADMainModel siadMainModel=response.body();
                if(siadMainModel!=null){
                    if (siadMainModel.getMessage().equalsIgnoreCase("true")){
                        setTimer();
//                        tv_testName.setText(OnlineTestData.paperName);
                        SIADDataModel siadDataModel=siadMainModel.getData();
                        siaddQuestionDataModelList=new ArrayList<>(Arrays.asList(siadDataModel.getQuestionData()));
                        studentQAModelList=setStudentQA(siaddQuestionDataModelList);
                        adapter = new TestQuestionAdapter(TestOnlineActivity.this, siaddQuestionDataModelList);
                        all_onlinequestion.setAdapter(adapter);
                        progressBarUtil.hideProgress();

                        //List<SIADDQuestionSectionModel> siaddQuestionSectionModelList=new ArrayList<>(Arrays.asList(siadDataModel.getQuestionSection()));
//                        totalQuestion=siaddQuestionDataModelList.size();
//                        setStudentQA();
//                        setQuestion(currentQuestion);
//                        layout_question.setVisibility(View.VISIBLE);
                    }
                    else
                        Toast.makeText(TestOnlineActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(TestOnlineActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<SIADMainModel> call, Throwable t) {
                System.out.println("call fail "+t);
                progressBarUtil.hideProgress();
            }
        });
    }
    private void setTimer() {
        countDownTimer=new CountDownTimer(milliTimer,1000) {
            @Override
            public void onTick(long l) {
                int hrs= (int) (l/(60*60*1000));
                long remainingMs= l%(60*60*1000);
                int min= (int) (remainingMs/(60*1000));
                int sec= (int) ((remainingMs%(60*1000))/1000);
                getTime(hrs);
                tv_timer.setText(""+getTime(hrs)+":"+getTime(min)+":"+getTime(sec));

                long fTime=cntMillitimer-l;

                ReHrs= (int) (fTime/(60*60*1000));
                long RmSEC= fTime%(60*60*1000);
                ReMin= (int) (RmSEC/(60*1000));
                ReSec= (int) ((RmSEC%(60*1000))/1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(TestOnlineActivity.this, "Time Up, Submitting Response", Toast.LENGTH_SHORT).show();
                //submitData();
            }
        }.start();
    }
    private String getTime(int timeData) {
        String time=String.valueOf(timeData);
        if (time.length()==1)
            return "0"+time;
        return time;
    }
    private List<StudentQAModel> setStudentQA(List<SIADDQuestionDataModel> list) {
        List<StudentQAModel> studentQAModelList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            StudentQAModel studentQAModel=new StudentQAModel();
            studentQAModel.SectionCode=list.get(i).getSectionCode();
            studentQAModel.QuestionID=list.get(i).getQuestionID();
            studentQAModel.selectedAns="";
            studentQAModel.QuestionGUID=list.get(i).getQuestionGUID();
            studentQAModel.ansStatusCode="0";
            studentQAModel.ansStatus="not_visited";
            studentQAModelList.add(studentQAModel);
        }
        OnlineTestData.studentQAModels=studentQAModelList;
        return studentQAModelList;
    }
    public void submitData(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Submitting Answers.....");
        pd.show();
       // List<StudentQAModel> studentQAModelList=OnlineTestData.studentQAModels;
        JSONArray Response = new JSONArray();
        for(int i=0;i<studentQAModelList.size();i++){
            StudentQAModel studentQAModel=studentQAModelList.get(i);
            JSONObject questionData=new JSONObject();
            try {
                questionData.put("K",studentQAModel.getQuestionGUID());
                questionData.put("V",studentQAModel.getSelectedAns());
                questionData.put("T",studentQAModel.getAnsStatusCode());
                questionData.put("DB",studentQAModel.getQuestionID());
                questionData.put("G","1");
                System.out.println("Results "+"K "+studentQAModel.getQuestionGUID()+" V "+studentQAModel.getSelectedAns()+" T "+studentQAModel.getAnsStatusCode()+" DB "+studentQAModel.getQuestionID());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Response.put(questionData);
        }

        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<ResultModel> call=apiClient.submitResponse(OnlineTestData.CoachingID,OnlineTestData.paperID,UserId,Response,null,true);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel resultModel=response.body();
                pd.cancel();
                if(resultModel!=null){
                    Toast.makeText(TestOnlineActivity.this, "Response Submitted", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(TestOnlineActivity.this, ViewResultActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(TestOnlineActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                pd.cancel();
                Toast.makeText(TestOnlineActivity.this, "Failed to submit", Toast.LENGTH_SHORT).show();
                System.out.println("call fail "+t);
            }
        });
    }

    public void showBottomSheetDialogFragment() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void showResult(){
        countDownTimer.cancel();
        bottomSheetResultFragment.show(getSupportFragmentManager(), bottomSheetResultFragment.getTag());
    }

    public void questionSelected(int position) {
        bottomSheetFragment.dismiss();
        all_onlinequestion.scrollToPosition(position);

    }

    public void backToTest(){
        bottomSheetResultFragment.dismiss();
    }
}
