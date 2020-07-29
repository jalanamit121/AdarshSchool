package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Adapter.AdsSemesterTopicAdapter;
import com.winbee.adarshsardarshahar.Models.SemesterName;
import com.winbee.adarshsardarshahar.Models.UrlName;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsSemesterTopicActivity extends AppCompatActivity {
    private SemesterName semester_data;
    private List<UrlName> list;
    private RecyclerView video_list_recycler;
    private AdsSemesterTopicAdapter adapter;
    LinearLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_semester_topic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        video_list_recycler = findViewById(R.id.gec_semester_topic_recycle);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        progressBarUtil   =  new ProgressBarUtil(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            semester_data = (SemesterName)bundle.getSerializable("semester_name");
            callTopicApiService();

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
                Intent intent = new Intent(AdsSemesterTopicActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });

    }
    private void callTopicApiService(){
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<UrlName>> call = mService.getTopic(2,semester_data.getBucket_ID(),semester_data.getChild_Link());
        // Call<ArrayList<BranchName>> call = mService.getBranchId(1,"WB_001","B_001_001");
        call.enqueue(new Callback<ArrayList<UrlName>>() {
            @Override
            public void onResponse(Call<ArrayList<UrlName>> call, Response<ArrayList<UrlName>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AdsSemesterTopicAdapter(AdsSemesterTopicActivity.this,list);
                    video_list_recycler.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UrlName>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topic_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.topic_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQueryHint("Search Topic");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTopicByName(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTopicByName(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchTopicByName(String s) {
        List<UrlName> filteredList = new ArrayList<>();
        try{
            for(int i=0;i<list.size();i++) {
                String subjectName = "", topic="", type="" ;

                if(list.get (i).getSubject()!=null || list.get (i).getTopic()!=null  ){
                    subjectName= list.get (i).getSubject();
                    topic= list.get (i).getTopic();
                    //type= list.get(i).getType();
                }

                if(subjectName.toLowerCase().contains(s.toLowerCase()) || topic.toLowerCase().contains(s.toLowerCase()) ) {
                    filteredList.add(list.get(i));
                }
            }

            adapter = new AdsSemesterTopicAdapter(AdsSemesterTopicActivity.this, filteredList);
            video_list_recycler.setAdapter (adapter);
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }


}
