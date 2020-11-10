package com.winbee.adarshsardarshahar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.winbee.adarshsardarshahar.Adapter.NotificationAdapter;
import com.winbee.adarshsardarshahar.Models.NotificationModel;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.LocalData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsNotificationActivity extends AppCompatActivity {
    private ArrayList<NotificationModel> notificationModel;
    private RecyclerView recyler_notification;
    private ProgressBarUtil progressBarUtil;
    private NotificationAdapter adapter;
    RelativeLayout today_classes;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_notification);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil   =  new ProgressBarUtil(this);
        recyler_notification = findViewById(R.id.notification);
        today_classes = findViewById(R.id.today_classes);
        callNotificationApiService();
    }
    private void callNotificationApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<NotificationModel>> call = apiCAll.getNotification(1, LocalData.org_code,UserId);
        call.enqueue(new Callback<ArrayList<NotificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationModel>> call, Response<ArrayList<NotificationModel>> response) {
                int statusCode = response.code();
                notificationModel = new ArrayList();
                if(statusCode==200 ){
                    if (response.body().size()!=0) {
                        System.out.println("Suree body: " + response.body());
                        notificationModel = response.body();
                        adapter = new NotificationAdapter(AdsNotificationActivity.this, notificationModel);
                        recyler_notification.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    }else{
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

}
