package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.adarshsardarshahar.Adapter.SolutionAdapter;
import com.winbee.adarshsardarshahar.Models.SolutionDoubtQuestion;
import com.winbee.adarshsardarshahar.Models.SolutionQuestion;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.LocalData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoubtSolutionActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
   private SolutionAdapter adapter;
    private ArrayList<SolutionQuestion> list;
    private RecyclerView askedSolution;
    EditText editTextGiveSolution;
    ImageView submit_solution,WebsiteHome,img_share;
    private TextView txt_user,txt_time,txt_ask_title,txt_ask_question,txt_commments;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_solution);
        askedSolution = findViewById(R.id.gec_asked_solution_recycle);
        editTextGiveSolution=findViewById(R.id.editTextGiveSolution);
        submit_solution=findViewById(R.id.submit_solution);
        txt_user=findViewById(R.id.txt_user);
        txt_user.setText(LocalData.User);
        txt_time=findViewById(R.id.txt_time);
        txt_time.setText(LocalData.Duration);
        txt_ask_title=findViewById(R.id.txt_ask_title);
        txt_ask_title.setText(LocalData.Title);
        txt_ask_question=findViewById(R.id.txt_ask_question);
        txt_ask_question.setText(LocalData.Discription);
        txt_commments=findViewById(R.id.txt_commments);
        txt_commments.setText(LocalData.Commnts);

        submit_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solutionValidation();
            }
        });
        progressBarUtil   =  new ProgressBarUtil(this);
        callAskedSolutionApiService();
    }
    private void callAskedSolutionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<SolutionQuestion>> call = apiCall.getSolution(LocalData.FileName);
        // Call<ArrayList<UrlQuestion>> call = mService.getQuestion(urlName.getDocumentId());

        call.enqueue(new Callback<ArrayList<SolutionQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<SolutionQuestion>> call, Response<ArrayList<SolutionQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new SolutionAdapter(DoubtSolutionActivity.this,list);
                    askedSolution.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SolutionQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" , Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void solutionValidation() {
        final String solution = editTextGiveSolution.getText().toString();
        final String question = LocalData.FileName;
        final String userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        Log.d("tag", "solutionValidation: "+solution+question+userid);
        if (TextUtils.isEmpty(solution)) {
            editTextGiveSolution.setError("Please enter solution");
            editTextGiveSolution.requestFocus();
            return;
        }
        SolutionDoubtQuestion solutionDoubtQuestion = new SolutionDoubtQuestion();
        solutionDoubtQuestion.setFilename(question);
        solutionDoubtQuestion.setAnswer(solution);
        solutionDoubtQuestion.setUserid(userid);



        callSolutionApiService(solutionDoubtQuestion);

    }
    private void callSolutionApiService(SolutionDoubtQuestion solutionDoubtQuestion) {
        progressBarUtil.showProgress();
        // final UrlQuestionSolution urlQuestionSolution = new UrlQuestionSolution(solution, question,userid, documentid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<SolutionDoubtQuestion> call = apiCall.getNewSolution(solutionDoubtQuestion.getUserid(),solutionDoubtQuestion.getFilename(),solutionDoubtQuestion.getAnswer());
        Log.d("tag", "callSolutionApiService: "+solutionDoubtQuestion.getFilename()+solutionDoubtQuestion.getAnswer()+solutionDoubtQuestion.getUserid());
        call.enqueue(new Callback<SolutionDoubtQuestion>() {
            @Override
            public void onResponse(Call<SolutionDoubtQuestion> call, Response<SolutionDoubtQuestion> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    progressBarUtil.hideProgress();
                    startActivity(new Intent(DoubtSolutionActivity.this, DoubtSolutionActivity.class));
                    finish();
                } else {
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SolutionDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: " + t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}
