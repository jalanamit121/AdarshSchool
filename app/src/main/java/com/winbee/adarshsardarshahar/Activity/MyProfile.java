package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;


public class MyProfile extends AppCompatActivity {

    ImageView imageView;
    TextView textViewName,textViewEmail,profile_whatapp;
    private String CurrentUserName,CurrentUserEmail,CurrentUserNumber;
    LinearLayout resetPassword,linearLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" My Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5252")));
        textViewName=findViewById(R.id.profile_username);
        textViewEmail=findViewById(R.id.profile_useremail);
        profile_whatapp=findViewById(R.id.profile_whatapp);
        CurrentUserName = SharedPrefManager.getInstance(this).refCode().getName();
        CurrentUserEmail = SharedPrefManager.getInstance(this).refCode().getEmail();
        CurrentUserNumber = SharedPrefManager.getInstance(this).refCode().getWhatsaapNo();
        textViewName.setText(CurrentUserName);
        textViewEmail.setText(CurrentUserEmail);
        profile_whatapp.setText(AssignmentData.Whatsaap);
        resetPassword=findViewById(R.id.resetPasswordlinearLayout);
        linearLayout3=findViewById(R.id.linearLayout3);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MyProfile.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MyProfile.this,WhatsAppActivity.class);
                startActivity(intent);
            }
        });

    }
}
