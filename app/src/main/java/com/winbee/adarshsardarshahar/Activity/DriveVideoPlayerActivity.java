package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Adapter.AdsAskedQuestionAdapter;
import com.winbee.adarshsardarshahar.Models.UrlName;
import com.winbee.adarshsardarshahar.Models.UrlQuestion;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;
import com.winbee.adarshsardarshahar.helper.MyWebChromeClient;
import com.winbee.adarshsardarshahar.helper.VideoEnabledWebView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriveVideoPlayerActivity extends AppCompatActivity {
    private VideoEnabledWebView andExoPlayerView;
    LinearLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;
    private AdsAskedQuestionAdapter adapter;
    private ArrayList<UrlQuestion> list;
    private UrlName urlName;
    private RecyclerView askedQuestion;
    private Button btm_asked_question;
    private RelativeLayout today_classes;
    private String TEST_URL_HLS = "https://drive.google.com/file/d/1AOroyeM5P8ZwZ15EGqR4SvRDW7lHoqeZ/preview";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive_videoplayer_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Log.i("ïnfo","Launched new activity");
        Intent intent = getIntent();
        String url = intent.getStringExtra("dURL");
        //String newURL=url.replace("view","preview");
       // String newURL=url.replace("view","preview");
        String array1[]= url.split("view");

        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        askedQuestion = findViewById(R.id.gec_asked_question_recycle);
        progressBarUtil   =  new ProgressBarUtil(this);
        btm_asked_question=findViewById(R.id.btm_asked_question);
        today_classes=findViewById(R.id.today_classes);
        btm_asked_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriveVideoPlayerActivity.this,AskedNewQuestionActivity.class);
                intent.putExtra("documentID",AssignmentData.DocumentId);
                startActivity(intent);
            }
        });

        CookieManager.getInstance().setAcceptCookie(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(andExoPlayerView, true);
        }

        WebSettings webSettings = andExoPlayerView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        andExoPlayerView.setWebViewClient(new WebViewClient());
        andExoPlayerView.setWebChromeClient(new MyWebChromeClient());
        andExoPlayerView.setWebChromeClient(new MyChrome());
        andExoPlayerView.loadUrl(array1[0]+"preview");
        Log.i("ïnfo","Launched new activity"+array1[0]);
        //Toast.makeText(this, "URL-"+newURL, Toast.LENGTH_SHORT).show();

        callAskedQuestionApiService();
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private void callAskedQuestionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<UrlQuestion>> call =apiCall.getQuestion(AssignmentData.DocumentId);
        call.enqueue(new Callback<ArrayList<UrlQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<UrlQuestion>> call, Response<ArrayList<UrlQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AdsAskedQuestionAdapter(DriveVideoPlayerActivity.this,list);
                    askedQuestion.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UrlQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
               // Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();
                today_classes.setVisibility(View.VISIBLE);

            }
        });
    }


}
