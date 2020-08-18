package com.winbee.adarshsardarshahar.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.winbee.adarshsardarshahar.Models.RefCode;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.Constants;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {


    EditText editTextUsername, editTextPassword;
    TextView referalCode;
    private long backPressedTime;
    private ProgressBarUtil progressBarUtil;
    LinearLayout forgetPassword;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, AdsHomeActivity.class));
            return;
        }
        initViews();
    }

    private void initViews() {
        progressBarUtil = new ProgressBarUtil(this);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        referalCode = (TextView) findViewById(R.id.link_referal_code);
        forgetPassword=(LinearLayout) findViewById(R.id.link_forget_password);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }
        IMEINumber = telephonyManager.getDeviceId();


        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userValidation();
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void userValidation() {
        final String mobile = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String referaCode = referalCode.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(mobile)) {
            editTextUsername.setError("Please enter your mobile no");
            editTextUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
       RefCode refCode = new RefCode();
        refCode.setUsername(mobile);
        refCode.setPassword(password);
        refCode.setRef_code(referaCode);
        callRefCodeSignInApi(refCode);



    }

    private void callRefCodeSignInApi(final RefCode refCode){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeSignIn(1,refCode.getUsername(),refCode.getPassword(),refCode.getRef_code(),IMEINumber);
        Log.i("tag", "callRefCodeSignInApi: "+IMEINumber);
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode  = response.code();
                if(statusCode==200 && response.body().getLoginStatus()!=false ) {
                    progressBarUtil.hideProgress();
                    AssignmentData.Whatsaap=response.body().getWhatsaapNo();
                    Constants.CurrentUser = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(Constants.CurrentUser);
                    startActivity(new Intent(LoginActivity.this, AdsHomeActivity.class));
                    finish();
                }else {
                        progressBarUtil.hideProgress();
                        Toast.makeText(LoginActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                    }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }



    @Override
    public void onBackPressed() {

        if (backPressedTime+2000>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(getBaseContext(),"Press Exit again to exit",Toast.LENGTH_SHORT).show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}