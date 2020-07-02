package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.winbee.adarshsardarshahar.Models.OtpVerify;
import com.winbee.adarshsardarshahar.Models.RefUser;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerficationActivity extends AppCompatActivity {
    //private OtpView otpView;
    Button otpVerify;
    private RefUser refuser;
    TextView mobile;
    EditText otp;
    private ProgressBarUtil progressBarUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verfication);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        otp = findViewById(R.id.link_otp);
//        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
//            @Override public void onOtpCompleted(String otp) {
//
//                Log.d("onOtpCompleted=>", otp);
//            }
//        });
        progressBarUtil = new ProgressBarUtil(this);
        otpVerify = findViewById(R.id.buttonOtp);
         mobile = (TextView) findViewById(R.id.text_mobile3);
        mobile.setText(message);
        otpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });
    }

    private void userValidation() {
        final String otp1 = otp.getText().toString();
        final String mobile1=mobile.getText().toString();

        if (TextUtils.isEmpty(otp1)) {
            otp.setError("Please enter your mobile no");
            otp.requestFocus();
            return;
        }

        OtpVerify otpVerify = new OtpVerify();
        otpVerify.setUsername(mobile1);
        otpVerify.setOtp(otp1);

        callOtpVerifySignInApi(otpVerify);
    }
    private void callOtpVerifySignInApi(final OtpVerify otpVerify){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<OtpVerify> call = mService.getOtpVerify(3,otpVerify.getUsername(),otpVerify.getOtp());
        call.enqueue(new Callback<OtpVerify>() {
            @Override
            public void onResponse(Call<OtpVerify> call, Response<OtpVerify> response) {
                int statusCode  = response.code();
                if(statusCode==200 && response.body().getSuccess()== true) {
                    progressBarUtil.hideProgress();
                    startActivity(new Intent(OtpVerficationActivity.this, LoginActivity.class));
                    finish();
                }else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(OtpVerficationActivity.this, "Invalid UserName Password ", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<OtpVerify> call, Throwable t) {
                Toast.makeText(OtpVerficationActivity.this,"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }


}
