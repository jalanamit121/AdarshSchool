package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.winbee.adarshsardarshahar.Adapter.AdsSemesterAdapter;
import com.winbee.adarshsardarshahar.Models.CourseDatum;
import com.winbee.adarshsardarshahar.Models.SemesterName;
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

public class MyCourseSubjectActivity extends AppCompatActivity {
    private CourseDatum courseDatum;
    private ArrayList<SemesterName> list;
    private RecyclerView video_list_recycler;
    private AdsSemesterAdapter adapter;
    LinearLayout home,histroy,logout,layout_doubt;
    private ProgressBarUtil progressBarUtil;
    SwipeRefreshLayout ads_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_subject);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        video_list_recycler = findViewById(R.id.gec_semester_recycle);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        ads_course=findViewById(R.id.ads_course);
        ads_course.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Bundle bundle = getIntent().getExtras();
                if(bundle!=null){
                    courseDatum = (CourseDatum) bundle.getSerializable("my_course");
                    callCourseSubjectApiService();

                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ads_course.setRefreshing(false);
                    }
                },4000);
            }
        });
        progressBarUtil   =  new ProgressBarUtil(this);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            courseDatum = (CourseDatum) bundle.getSerializable("my_course");
            callCourseSubjectApiService();

        }
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
                Intent intent = new Intent(MyCourseSubjectActivity.this,DiscussionActivity.class);
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
                Intent intent = new Intent(MyCourseSubjectActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void callCourseSubjectApiService(){
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<SemesterName>> call = mService.getCourseSubject(1,"WB_005",courseDatum.getChild_Link());
        // Call<ArrayList<BranchName>> call = mService.getBranchId(1,"WB_001","B_001_001");
        call.enqueue(new Callback<ArrayList<SemesterName>>() {
            @Override
            public void onResponse(Call<ArrayList<SemesterName>> call, Response<ArrayList<SemesterName>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AdsSemesterAdapter(MyCourseSubjectActivity.this,list);
                    video_list_recycler.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SemesterName>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}
