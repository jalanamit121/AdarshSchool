package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Adapter.SubmittedAdapter;
import com.winbee.adarshsardarshahar.Models.SubmittedAssignment;
import com.winbee.adarshsardarshahar.Models.SubmittedDatum;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmittedAssignmentActivity extends AppCompatActivity {
    private List<SubmittedDatum> list;
    RelativeLayout home,histroy,logout,today_classes;
    private ProgressBarUtil progressBarUtil;
    ScrollView scrollView;
    private SubmittedAdapter submittedAdapter;
    private RecyclerView assignmentView;
    String Userid;
    private Button btn_submitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_assignment);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        today_classes=findViewById(R.id.today_classes);
        Userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil   =  new ProgressBarUtil(this);
        assignmentView = findViewById(R.id.assignment_review);
        btn_submitted=findViewById(R.id.btn_submitted);
        btn_submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubmittedAssignmentActivity.this,AssignmentToSubmitActivity.class);
                startActivity(intent);

            }
        });

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
                Intent intent = new Intent(SubmittedAssignmentActivity.this,AdsHomeActivity.class);
                startActivity(intent);
            }
        });

        callAllAssignment(Userid);
    }
    private void callAllAssignment(String Userid) {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<SubmittedAssignment> call = apiCAll.getSubmitedAssignment("WB_005",Userid);
        call.enqueue(new Callback<SubmittedAssignment>() {
            @Override
            public void onResponse(Call<SubmittedAssignment> call, Response<SubmittedAssignment> response) {
                SubmittedAssignment submittedAssignment=response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getAssignment() == true) {
                        list = new ArrayList<>(Arrays.asList(submittedAssignment.getData()));
                        System.out.println("Suree body: " + response.body());
                        submittedAdapter = new SubmittedAdapter(SubmittedAssignmentActivity.this, list);
                        assignmentView.setAdapter(submittedAdapter);
                        progressBarUtil.hideProgress();
                    }else
                    {
                        today_classes.setVisibility(View.VISIBLE);
                    }
                }

                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmittedAssignment> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}

