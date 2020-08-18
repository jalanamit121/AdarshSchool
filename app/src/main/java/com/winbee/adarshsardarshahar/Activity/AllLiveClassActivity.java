package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.adarshsardarshahar.Adapter.AdsAllLiveClassesAdapter;
import com.winbee.adarshsardarshahar.Models.LiveClass;
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

public class AllLiveClassActivity extends AppCompatActivity {
    private ArrayList<LiveClass> liveList;
    private RecyclerView video_list_recycler;
    private ProgressBarUtil progressBarUtil;
    private AdsAllLiveClassesAdapter adapter;
    LinearLayout home,histroy,logout,layout_doubt;
    String UserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_live_class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil   =  new ProgressBarUtil(this);
        video_list_recycler = findViewById(R.id.all_liveClasses);
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
                Intent intent = new Intent(AllLiveClassActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllLiveClassActivity.this,DiscussionActivity.class);
                startActivity(intent);
            }
        });

        callLiveApiService();


    }
    private void callLiveApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<LiveClass>> call = apiCAll.getLive(3,"wb_005",UserId);
        call.enqueue(new Callback<ArrayList<LiveClass>>() {
            @Override
            public void onResponse(Call<ArrayList<LiveClass>> call, Response<ArrayList<LiveClass>> response) {
                int statusCode = response.code();
                liveList = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    liveList = response.body();
                    adapter = new AdsAllLiveClassesAdapter(AllLiveClassActivity.this,liveList);
                    video_list_recycler.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LiveClass>> call, Throwable t) {
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
