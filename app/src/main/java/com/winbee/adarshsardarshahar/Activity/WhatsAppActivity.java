package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.winbee.adarshsardarshahar.Models.WhatsAppData;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WhatsAppActivity extends AppCompatActivity {
    EditText forgetMobile;
    Button forgetButton;
    private ProgressBarUtil progressBarUtil;
    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app);

        forgetMobile = findViewById(R.id.editTextre_Password);
        progressBarUtil = new ProgressBarUtil(this);
        forgetButton = findViewById(R.id.buttonForget);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });
    }
    private void userValidation() {
        final String mobile = forgetMobile.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(mobile)) {
            forgetMobile.setError("Please enter your mobile no");
            forgetMobile.requestFocus();
            return;
        }
        WhatsAppData whatsAppData = new WhatsAppData();
        whatsAppData.setWhatsapp_mobile(mobile);
        callForgetMobileApi(whatsAppData);
    }
    private void callForgetMobileApi(final WhatsAppData whatsAppData){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<WhatsAppData> call = mService.getWhatsApp(UserId, AssignmentData.org_code,whatsAppData.getWhatsapp_mobile(),"true");
        call.enqueue(new Callback<WhatsAppData>() {
            @Override
            public void onResponse(Call<WhatsAppData> call, Response<WhatsAppData> response) {
                int statusCode  = response.code();
                if(statusCode==200 && response.body().getSuccess()== true) {
                    AssignmentData.Whatsaap=response.body().getWhatsapp_mobile();
                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(WhatsAppActivity.this,AdsHomeActivity.class);
                    startActivity(intent);

                }else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(WhatsAppActivity.this, "Something went wrong ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WhatsAppData> call, Throwable t) {
                Toast.makeText(WhatsAppActivity.this,"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }


}

