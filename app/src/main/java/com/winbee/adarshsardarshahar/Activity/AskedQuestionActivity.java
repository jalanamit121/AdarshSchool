package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Adapter.AdsAskedQuestionAdapter;
import com.winbee.adarshsardarshahar.Models.UrlName;
import com.winbee.adarshsardarshahar.Models.UrlQuestion;
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

public class AskedQuestionActivity extends AppCompatActivity {
    LinearLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;
    private AdsAskedQuestionAdapter adapter;
    private ArrayList<UrlQuestion> list;
    private RecyclerView askedQuestion;
    private UrlName urlName;
    private RelativeLayout today_classes;

    ImageView btmNewQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asked_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        askedQuestion = findViewById(R.id.gec_asked_question_recycle);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        progressBarUtil   =  new ProgressBarUtil(this);
        btmNewQuestion=findViewById(R.id.btmNewQuestion);
        today_classes=findViewById(R.id.today_classes);

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
                Intent intent = new Intent(AskedQuestionActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });
       final String documentID=getIntent().getStringExtra("documentID");
        if(documentID!=null)
            callAskedQuestionApiService(documentID);
        System.out.println("intent "+documentID);

        btmNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskedQuestionActivity.this, AskedNewQuestionActivity.class);
                intent.putExtra("documentID",documentID);
                startActivity(intent);
            }
        });

    }

    private void callAskedQuestionApiService(String documentID){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<UrlQuestion>> call =apiCall.getQuestion(documentID);
        call.enqueue(new Callback<ArrayList<UrlQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<UrlQuestion>> call, Response<ArrayList<UrlQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AdsAskedQuestionAdapter(AskedQuestionActivity.this,list);
                    askedQuestion.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UrlQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
               // Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();
                today_classes.setVisibility(View.VISIBLE);

            }
        });
    }



    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
}
