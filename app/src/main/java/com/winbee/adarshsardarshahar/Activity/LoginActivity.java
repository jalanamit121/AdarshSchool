package com.winbee.adarshsardarshahar.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.winbee.adarshsardarshahar.Models.RefCode;
import com.winbee.adarshsardarshahar.Models.ResendOtp;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.Constants;
import com.winbee.adarshsardarshahar.Utils.LocalData;
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
    String android_id,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android_id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("tag", "onCreate: "+android_id);
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
        username=SharedPrefManager.getInstance(this).refCode().getUsername();



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
    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_btn){

            if(editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                (( ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Show Password
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Hide Password
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

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

    private void loginValidation() {
        final String mobile = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
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
        ForceLogout(refCode);


    }

    private void callRefCodeSignInApi(final RefCode refCode){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeSignIn(1,refCode.getUsername(),refCode.getPassword(),refCode.getRef_code(),android_id);
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
                    if (response.body().getError_code()==1){// wrong password
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert_login);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        txt_login_verify.setVisibility(View.GONE);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        txt_login.setVisibility(View.GONE);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        txt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }else if (response.body().getError_code()==2){//already login
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert_login);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        txt_login_verify.setVisibility(View.GONE);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        txt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        txt_login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loginValidation();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else if (response.body().getError_code()==3){// verify pending
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);

                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert_login);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        txt_login.setVisibility(View.GONE);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        txt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        txt_login_verify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callResendOtp();
                                dialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this,OtpVerficationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialog.show();

                    }else if (response.body().getError_code()==4){// technical issue
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert_login);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        txt_cancel.setVisibility(View.GONE);
                        txt_login.setVisibility(View.GONE);
                        txt_login_verify.setVisibility(View.GONE);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        dialog.show();

                    }

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
    private void callResendOtp() {
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<ResendOtp> call = mService.getResendOtp( username,1);
        call.enqueue(new Callback<ResendOtp>() {
            @Override
            public void onResponse(Call<ResendOtp> call, Response<ResendOtp> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getSuccess() == true) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResendOtp> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }



    private void ForceLogout( final  RefCode refCode) {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeForceLogout(4,refCode.getUsername(),refCode.getPassword(), "ADS001",android_id);
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    progressBarUtil.hideProgress();
                    Constants.CurrentUser = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(Constants.CurrentUser);
                    Intent intent = new Intent(LoginActivity.this, AdsHomeActivity.class);
                    intent.putExtra("userid", refCode.getUserId());
                    intent.putExtra("username", response.body().getEmail());
                    intent.putExtra("password", response.body().getPassword());
                    LocalData.Password=response.body().getPassword();
                    LocalData.Email=response.body().getEmail();

                    startActivity(intent);
                    finish();

                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(LoginActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

}