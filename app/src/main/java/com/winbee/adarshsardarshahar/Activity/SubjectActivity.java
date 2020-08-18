package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Adapter.SubjectAdapter;
import com.winbee.adarshsardarshahar.Models.SectionDetailsDataModel;
import com.winbee.adarshsardarshahar.Models.SectionDetailsMainModel;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.adarshsardarshahar.Utils.OnlineTestData;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectActivity extends AppCompatActivity {
    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_subject;
    private Toast toast_msg;
    String UserId;
    LinearLayout home,histroy,logout,layout_doubt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        iniIDs();
        getSubjectName();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_subject=findViewById(R.id.recycle_subject);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubjectActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
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
                Intent intent = new Intent(SubjectActivity.this,DiscussionActivity.class);
                startActivity(intent);
            }
        });
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
    }
    private void getSubjectName() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SectionDetailsMainModel> call=apiClient.fetchSectionDetails(OnlineTestData.org_code,OnlineTestData.auth_code,UserId);
        call.enqueue(new Callback<SectionDetailsMainModel>() {
            @Override
            public void onResponse(Call<SectionDetailsMainModel> call, Response<SectionDetailsMainModel> response) {
                apiCalled();
                SectionDetailsMainModel sectionDetailsMainModel=response.body();
                if(sectionDetailsMainModel!=null){
                    if (sectionDetailsMainModel.getMessage().equalsIgnoreCase("TRUE")){
                        List<SectionDetailsDataModel> sectionDetailsDataModelList=new ArrayList<>(Arrays.asList(sectionDetailsMainModel.getData()));
                        SubjectAdapter subjectAdapter=new SubjectAdapter(SubjectActivity.this,sectionDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SubjectActivity.this, LinearLayoutManager.VERTICAL, false);
                        recycle_subject.setLayoutManager(layoutManager);
                        recycle_subject.setItemAnimator(new DefaultItemAnimator());
                        recycle_subject.setAdapter(subjectAdapter);
                    }
                    else
                        doToast(sectionDetailsMainModel.getMessage());
                }
                else
                    doToast("No Test Available");
            }
            @Override
            public void onFailure(Call<SectionDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
            }
        });
    }
    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }
    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(SubjectActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
}
