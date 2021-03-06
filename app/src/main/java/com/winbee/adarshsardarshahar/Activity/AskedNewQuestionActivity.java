package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.winbee.adarshsardarshahar.Models.UrlNewQuestion;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskedNewQuestionActivity extends AppCompatActivity {

    EditText editTextQuestionTitle,editTextQuestionDescription,editTextUserid,editTextDocumentId;
    private String CurrentUserName;
    private ProgressBarUtil progressBarUtil;
    Button submit;
    LinearLayout home,histroy,logout,layout_doubt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asked_new_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        editTextQuestionTitle=findViewById(R.id.editTextQuestionTitle);
        editTextQuestionDescription=findViewById(R.id.editTextQuestionDescription);
        editTextUserid=findViewById(R.id.editTextUserid);
        progressBarUtil   =  new ProgressBarUtil(this);
        editTextDocumentId=findViewById(R.id.editTextDocumentId);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);


        CurrentUserName =  SharedPrefManager.getInstance(this).refCode().getUserId();
        editTextUserid.setText(CurrentUserName);
        editTextDocumentId.setText(AssignmentData.DocumentId);

        submit=findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });

        histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://adarshsardarshahar.com/"));
                startActivity(intent);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskedNewQuestionActivity.this,DiscussionActivity.class);
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
                Intent intent = new Intent(AskedNewQuestionActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });




    }
    private void userValidation() {
        final String title = editTextQuestionTitle.getText().toString();
        final String description = editTextQuestionDescription.getText().toString();
        final String userid = editTextUserid.getText().toString();
        final String documentid = editTextDocumentId.getText().toString();

        if (TextUtils.isEmpty(title)) {
            editTextQuestionTitle.setError("Please enter title");
            editTextQuestionTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            editTextQuestionDescription.setError("Please enter description");
            editTextQuestionDescription.requestFocus();
            return;
        }

        UrlNewQuestion urlNewQuestion = new UrlNewQuestion();
        urlNewQuestion.setTitle(title);
        urlNewQuestion.setQuestion(description);
        urlNewQuestion.setUserId(userid);
        urlNewQuestion.setDocumentId(documentid);




        callNewAskedQuestionApiService(urlNewQuestion);

    }

    private void callNewAskedQuestionApiService(UrlNewQuestion urlNewQuestion){
        progressBarUtil.showProgress();
       // final UrlNewQuestion urlNewQuestion = new UrlNewQuestion(title,description,documentid,userid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<UrlNewQuestion> call =apiCall.getNewQuestion(urlNewQuestion.getTitle(),urlNewQuestion.getQuestion(),urlNewQuestion.getUserId(),urlNewQuestion.getDocumentId());
        Log.d("TAG", "callNewAskedQuestionApiService: "+urlNewQuestion.getTitle()+""+urlNewQuestion.getQuestion()+""+urlNewQuestion.getUserId()+""+urlNewQuestion.getDocumentId());
        call.enqueue(new Callback<UrlNewQuestion>() {
            @Override
            public void onResponse(Call<UrlNewQuestion> call, Response<UrlNewQuestion> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    progressBarUtil.hideProgress();
                    startActivity(new Intent(AskedNewQuestionActivity.this, AskedQuestionActivity.class));
                    finish();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UrlNewQuestion> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}
