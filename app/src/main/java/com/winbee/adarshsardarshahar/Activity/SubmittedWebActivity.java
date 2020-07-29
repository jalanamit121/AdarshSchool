package com.winbee.adarshsardarshahar.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.squareup.picasso.Picasso;
import com.winbee.adarshsardarshahar.Models.AssignmentDatum;
import com.winbee.adarshsardarshahar.Models.SubmittedDatum;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;

import java.util.Objects;

public class SubmittedWebActivity extends AppCompatActivity {
    private WebView webView;
    private SubmittedDatum assignmentDatum;
    private ProgressBarUtil progressBarUtil;
    LinearLayout home,histroy,logout;
    Button btm_asked_question;
    String googleDocs = "https://docs.google.com/viewer?url=";
    ImageView image_view;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitted_web);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        webView =findViewById(R.id.myWebView);
        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        image_view=findViewById(R.id.image_view);
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
                Intent intent = new Intent(SubmittedWebActivity.this, AdsHomeActivity.class);
                startActivity(intent);
            }
        });


        progressBarUtil   =  new ProgressBarUtil(this);
        progressBarUtil.showProgress();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            assignmentDatum = (SubmittedDatum) bundle.getSerializable("URL");
           // Toast.makeText()
        }
        if (AssignmentData.Content.endsWith("pdf")) {
            displayWebView();
        }else{
            image_view.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            Picasso.get().load(AssignmentData.Content).into(image_view);
        }
        displayWebView();
    }

    private void displayWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(googleDocs+AssignmentData.Content);
                return true;

            }
        });
        webView.loadUrl(googleDocs+AssignmentData.Content);

    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}
