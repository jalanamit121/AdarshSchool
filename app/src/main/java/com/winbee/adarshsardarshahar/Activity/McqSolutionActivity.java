package com.winbee.adarshsardarshahar.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.adarshsardarshahar.Adapter.McqSolutionAdapter;
import com.winbee.adarshsardarshahar.Models.McqSolutionModel;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.LocalData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class McqSolutionActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
    private McqSolutionAdapter adapter;
    private ArrayList<McqSolutionModel> list;
    private RecyclerView askedSolution;
    String UserId,UserName;
    ImageView WebsiteHome,img_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_solution);
        progressBarUtil   =  new ProgressBarUtil(this);
        askedSolution = findViewById(R.id.gec_asked_solution_recycle);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        UserName=SharedPrefManager.getInstance(this).refCode().getName();
        callAskedSolutionApiService();
    }
    private void callAskedSolutionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<McqSolutionModel>> call = apiCall.getMcqQuestionSolution(LocalData.QuestionID,UserId,UserName);
        // Call<ArrayList<UrlQuestion>> call = mService.getQuestion(urlName.getDocumentId());

        call.enqueue(new Callback<ArrayList<McqSolutionModel>>() {
            @Override
            public void onResponse(Call<ArrayList<McqSolutionModel>> call, Response<ArrayList<McqSolutionModel>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new McqSolutionAdapter(McqSolutionActivity.this,list);
                    askedSolution.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<McqSolutionModel>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" , Toast.LENGTH_SHORT).show();

            }
        });
    }


}
