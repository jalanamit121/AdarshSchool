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


import com.winbee.adarshsardarshahar.Adapter.TestListAdapter;
import com.winbee.adarshsardarshahar.Models.SIACDetailsDataModel;
import com.winbee.adarshsardarshahar.Models.SIACDetailsMainModel;
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


public class OnlineTestActivity extends AppCompatActivity {
    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_test;
    private Toast toast_msg;
    String UserId;
    LinearLayout home,histroy,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_test);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        iniIDs();
        getTestList();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_test=findViewById(R.id.recycle_test);
        home=findViewById(R.id.layout_home);
        UserId=SharedPrefManager.getInstance(this).refCode().getUserId();
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnlineTestActivity.this, AdsHomeActivity.class);
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
    }
    private void getTestList() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails(OnlineTestData.org_code,OnlineTestData.auth_code,OnlineTestData.bucketID,UserId);
        call.enqueue(new Callback<SIACDetailsMainModel>() {
            @Override
            public void onResponse(Call<SIACDetailsMainModel> call, Response<SIACDetailsMainModel> response) {
                apiCalled();
                SIACDetailsMainModel siacDetailsMainModel=response.body();
                if(siacDetailsMainModel!=null){
                    if (siacDetailsMainModel.getMessage().equalsIgnoreCase("true")){
                        List<SIACDetailsDataModel> siacDetailsDataModelList=new ArrayList<>(Arrays.asList(siacDetailsMainModel.getData()));
                        TestListAdapter testListAdapter=new TestListAdapter(OnlineTestActivity.this,siacDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(OnlineTestActivity.this, LinearLayoutManager.VERTICAL, false);
                        recycle_test.setLayoutManager(layoutManager);
                        recycle_test.setItemAnimator(new DefaultItemAnimator());
                        recycle_test.setAdapter(testListAdapter);
                    }
                    else
                        doToast(siacDetailsMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SIACDetailsMainModel> call, Throwable t) {
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
        toast_msg = Toast.makeText(OnlineTestActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
}
