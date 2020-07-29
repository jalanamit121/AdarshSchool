package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.adarshsardarshahar.Adapter.AdsAskedSolutionAdapter;
import com.winbee.adarshsardarshahar.Models.UrlQuestion;
import com.winbee.adarshsardarshahar.Models.UrlQuestionSolution;
import com.winbee.adarshsardarshahar.Models.UrlSolution;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskedSolutionActivity extends AppCompatActivity {
    LinearLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;
    private AdsAskedSolutionAdapter adapter;
    private ArrayList<UrlSolution> list;
    private RecyclerView askedSolution;
    private UrlQuestion urlQuestion;
    EditText editTextGiveSolution,editTextQuestionid,editTextDocumentid;
    ImageView submit_solution;
    TextView editTextUserid;
    private String CurrentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asked_solution);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        askedSolution = findViewById(R.id.gec_asked_solution_recycle);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        editTextGiveSolution=findViewById(R.id.editTextGiveSolution);

        editTextUserid=findViewById(R.id.editTextUserid);
        CurrentUserName =  SharedPrefManager.getInstance(this).refCode().getUserId();
        editTextUserid.setText(CurrentUserName);

        submit_solution=findViewById(R.id.submit_solution);
        submit_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solutionValidation();
            }
        });
        progressBarUtil   =  new ProgressBarUtil(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            urlQuestion = (UrlQuestion)bundle.getSerializable("file_name");
            callAskedSolutionApiService();
            editTextQuestionid=findViewById(R.id.editTextQuestionid);
            editTextQuestionid.setText(urlQuestion.getFile_name());
            editTextDocumentid=findViewById(R.id.editTextDocumentid);
            editTextDocumentid.setText(urlQuestion.getDocumentId());

        }


        histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://adarshsardarshahar.com/"));
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskedSolutionActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });
    }


    private void callAskedSolutionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<UrlSolution>> call = apiCall.getSolution(urlQuestion.getDocumentId(),urlQuestion.getFile_name());
        // Call<ArrayList<UrlQuestion>> call = mService.getQuestion(urlName.getDocumentId());

        call.enqueue(new Callback<ArrayList<UrlSolution>>() {
            @Override
            public void onResponse(Call<ArrayList<UrlSolution>> call, Response<ArrayList<UrlSolution>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AdsAskedSolutionAdapter(AskedSolutionActivity.this,list);
                    askedSolution.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UrlSolution>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" ,Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void solutionValidation() {
        final String solution = editTextGiveSolution.getText().toString();
        final String question = editTextQuestionid.getText().toString();
        final String userid = editTextUserid.getText().toString();
        final String documentid = editTextDocumentid.getText().toString();

        if (TextUtils.isEmpty(solution)) {
            editTextGiveSolution.setError("Please enter solution");
            editTextGiveSolution.requestFocus();
            return;
        }
     UrlQuestionSolution urlQuestionSolution = new UrlQuestionSolution();
        urlQuestionSolution.setFilename(question);
        urlQuestionSolution.setAnswer(solution);
        urlQuestionSolution.setDocumentId(documentid);
        urlQuestionSolution.setUserId(userid);



        callSolutionApiService(urlQuestionSolution);

    }
    private void callSolutionApiService(UrlQuestionSolution urlQuestionSolution) {
        progressBarUtil.showProgress();
       // final UrlQuestionSolution urlQuestionSolution = new UrlQuestionSolution(solution, question,userid, documentid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<UrlQuestionSolution> call = apiCall.getQuestionSolution(urlQuestionSolution.getFilename(),urlQuestionSolution.getAnswer(),urlQuestionSolution.getDocumentId(),urlQuestionSolution.getUserId());
        call.enqueue(new Callback<UrlQuestionSolution>() {
            @Override
            public void onResponse(Call<UrlQuestionSolution> call, Response<UrlQuestionSolution> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    progressBarUtil.hideProgress();
                    startActivity(new Intent(AskedSolutionActivity.this, AskedQuestionActivity.class));
                    finish();
                } else {
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UrlQuestionSolution> call, Throwable t) {
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
