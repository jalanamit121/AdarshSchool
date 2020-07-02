package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.adarshsardarshahar.Models.ViewResult;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.adarshsardarshahar.Utils.OnlineTestData;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewResultActivity extends AppCompatActivity {

    private TextView tv_paper_name,tv_section_name,tv_total_question,tv_total_attempt,tv_total_correct,tv_total_review,tv_total_wrong,tv_total_marks;
    private Button backbtn;
    String UserID;
    private ViewResult viewResult;
    private List<ViewResult> list;
    private RecyclerView view_result_recycle;
    //private ViewResultAdapter viewResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        tv_paper_name=findViewById(R.id.tv_paper_name);
        tv_section_name=findViewById(R.id.tv_section_name);
        tv_total_question=findViewById(R.id.tv_total_question);
        tv_total_attempt=findViewById(R.id.tv_total_attempt);
        tv_total_correct=findViewById(R.id.tv_total_correct);
        tv_total_review=findViewById(R.id.tv_total_review);
        tv_total_wrong=findViewById(R.id.tv_total_wrong);
        tv_total_marks=findViewById(R.id.tv_total_marks);
        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        backbtn=findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ViewResultActivity.this,AdsHomeActivity.class);
                startActivity(intent);
            }
        });


        callApiService();

    }

    private void callApiService() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<ViewResult> call = apiCAll.viewResult("WB_005", OnlineTestData.paperID,UserID);
        call.enqueue(new Callback<ViewResult>() {
            @Override
            public void onResponse(Call<ViewResult> call, Response<ViewResult> response) {
                list = new ArrayList();
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    System.out.println("Suree body: "+response.body());
                    OnlineTestData.PaperID=response.body().getPaperID();
                    String Question=String.valueOf(response.body().getTotalQuestion());
                    tv_total_question.setText(Question);
                    String Attempt=String.valueOf(response.body().getAttempt());
                    tv_total_attempt.setText(Attempt);
                    String Correct=String.valueOf(response.body().getCorrect());
                    tv_total_correct.setText(Correct);
                    String Review=String.valueOf(response.body().getReview());
                    tv_total_review.setText(Review);
                    String Wrong=String.valueOf(response.body().getWrong());
                    tv_total_wrong.setText(Wrong);
                    String Total=response.body().getTotalMarks();
                    tv_total_marks.setText(Total);

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
        tv_paper_name.setText(OnlineTestData.paperName);
        tv_section_name.setText(OnlineTestData.paperName);
    }

}
