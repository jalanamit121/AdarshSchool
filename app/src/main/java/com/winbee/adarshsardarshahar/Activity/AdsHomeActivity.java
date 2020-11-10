package com.winbee.adarshsardarshahar.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.winbee.adarshsardarshahar.Adapter.AdsHomeAdapter;
import com.winbee.adarshsardarshahar.Adapter.AdsHomeLiveAdapter;
import com.winbee.adarshsardarshahar.BuildConfig;
import com.winbee.adarshsardarshahar.Models.AttendenceModel;
import com.winbee.adarshsardarshahar.Models.BannerModel;
import com.winbee.adarshsardarshahar.Models.BranchName;
import com.winbee.adarshsardarshahar.Models.CourseDatum;
import com.winbee.adarshsardarshahar.Models.LiveClass;
import com.winbee.adarshsardarshahar.Models.LogOut;
import com.winbee.adarshsardarshahar.Models.PurchasedMainModel;
import com.winbee.adarshsardarshahar.Models.RefCode;
import com.winbee.adarshsardarshahar.NewModels.Attendence;
import com.winbee.adarshsardarshahar.NewModels.LiveClassContent;
import com.winbee.adarshsardarshahar.NewModels.LiveClassContentaArray;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.LinePagerIndicatorDecoration;
import com.winbee.adarshsardarshahar.Utils.LocalData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<CourseDatum> list;
    private ArrayList<BannerModel> bannerModel;
    private ArrayList<LiveClassContentaArray> liveList;
    private RecyclerView video_list_recycler,video_list_recycler1;
    private AdsHomeAdapter adapter;
    private AdsHomeLiveAdapter liveAdapter;
    ScrollView scrollView;
    RelativeLayout layout_noCourse,layout_welcome,layout_myClass,layout_online_test,layout_assignment,layout_attendence;
    private ArrayList<RefCode> refCodeData;
    private ProgressBarUtil progressBarUtil;
    boolean doubleBackToExitPressedOnce = false;
    TextView viewAll,nocourse,noclasses;
    String sCurrentVersion,sLastestVersion,Userid,android_id,UserMobile,UserPassword;
    SwipeRefreshLayout ads_home;
    ImageSlider imageSlider;
    LinearLayout home,histroy,logout,layout_doubt;
    boolean version = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_home);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        //firebase push notification
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("adarshschool","adarshschool", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        FirebaseMessaging.getInstance().subscribeToTopic("adarshschool")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                    }
                });

        new GetLastesVersion().execute();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        scrollView=findViewById(R.id.scroll_view);
        video_list_recycler = findViewById(R.id.gec_home_recycle);
        AdsHomeAdapter myAdapter = new AdsHomeAdapter(this,list);
        video_list_recycler.setLayoutManager(new GridLayoutManager(this,2));
        video_list_recycler.setAdapter(myAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        video_list_recycler1 = findViewById(R.id.gec_home_recycle1);
        video_list_recycler1.setLayoutManager(layoutManager);
        AdsHomeLiveAdapter myAdapter1 = new AdsHomeLiveAdapter(this,liveList);
        video_list_recycler1.setAdapter(myAdapter1);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(video_list_recycler1);
        video_list_recycler1.addItemDecoration(new LinePagerIndicatorDecoration());
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        Userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        android_id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);


        nocourse=findViewById(R.id.nocourse);
        noclasses=findViewById(R.id.noclasses);
        layout_online_test=findViewById(R.id.layout_online_test);
        layout_assignment=findViewById(R.id.layout_assignment);
        layout_noCourse=findViewById(R.id.layout_noCourse);
        layout_welcome=findViewById(R.id.layout_welcome);
        layout_myClass=findViewById(R.id.layout_myClass);
        ads_home=findViewById(R.id.ads_home);
        ads_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApiService(Userid);
                callLiveApiService();
                callBannerService();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ads_home.setRefreshing(false);
                    }
                },4000);
            }
        });
        layout_attendence=findViewById(R.id.layout_attendence);
        layout_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsHomeActivity.this,AssignmentToSubmitActivity.class);
                startActivity(intent);
            }
        });

        layout_attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAttendenceService();
            }
        });
        viewAll=findViewById(R.id.text_viewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdsHomeActivity.this,AllLiveClassActivity.class);
                startActivity(intent);
            }
        });
        progressBarUtil   =  new ProgressBarUtil(this);
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
                Intent intent = new Intent(AdsHomeActivity.this,AdsHomeActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsHomeActivity.this,DiscussionActivity.class);
                startActivity(intent);
            }
        });
        layout_online_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsHomeActivity.this,SubjectActivity.class);
                startActivity(intent);
            }
        });

        imageSlider =findViewById(R.id.slider);
        callApiService(Userid);
        callLiveApiService();
        callBannerService();



    }

    // for geting all live class
    private void callLiveApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<LiveClassContent> call = apiCAll.getLive(3, LocalData.org_code,Userid,android_id);
        call.enqueue(new Callback<LiveClassContent>() {
            @Override
            public void onResponse(Call<LiveClassContent> call, Response<LiveClassContent> response) {
                LiveClassContent liveClassContent = response.body();
                int statusCode = response.code();
                liveList = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getError()==false) {
                        System.out.println("Suree body: " + response.body());
                        liveList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(liveClassContent).getData()));
                        if (liveList.size()!=0){
                            noclasses.setVisibility(View.GONE);
                        liveAdapter = new AdsHomeLiveAdapter(AdsHomeActivity.this, liveList);
                        video_list_recycler1.setAdapter(liveAdapter);
                        progressBarUtil.hideProgress();
                        }else{
                            progressBarUtil.hideProgress();
                            noclasses.setVisibility(View.VISIBLE);
                        }

                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                AdsHomeActivity.this);
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LiveClassContent> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    //for attendeence
    private void callAttendenceService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<Attendence> call = apiCAll.fetchAttendence(Userid,android_id);
        call.enqueue(new Callback<Attendence>() {
            @Override
            public void onResponse(Call<Attendence> call, Response<Attendence> response) {
                int statusCode = response.code();
                liveList = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getError()==false){
                    AssignmentData.AttendenceMessage=response.body().getData().getMessage();
                    updateAttendenceDialog();
                    progressBarUtil.hideProgress();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                AdsHomeActivity.this);
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Network Issues" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Attendence> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }


    // getting all course assign to one user
    private void callApiService(String Userid) {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<PurchasedMainModel> call = apiCAll.getCourseById(1,Userid,LocalData.org_code,android_id);
        call.enqueue(new Callback<PurchasedMainModel>() {
            @Override
            public void onResponse(Call<PurchasedMainModel> call, Response<PurchasedMainModel> response) {
                PurchasedMainModel purchasedMainModel=response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    if (response.body().getPurchased()== true) {
                        layout_noCourse.setVisibility(View.VISIBLE);
                        layout_welcome.setVisibility(View.INVISIBLE);
                        if (purchasedMainModel !=null) {
                            list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(purchasedMainModel).getCourseData()));
                            System.out.println("Suree body: " + response.body());
                            adapter = new AdsHomeAdapter(AdsHomeActivity.this, list);
                            video_list_recycler.setAdapter(adapter);
                            progressBarUtil.hideProgress();
                        }
                    }else{
                        layout_noCourse.setVisibility(View.INVISIBLE);
                        layout_welcome.setVisibility(View.VISIBLE);
                        nocourse.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();

                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchasedMainModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }


    // for banner display
    private void callBannerService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<BannerModel>> call = apiCAll.getBanner(LocalData.org_code);
        call.enqueue(new Callback<ArrayList<BannerModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BannerModel>> call, Response<ArrayList<BannerModel>> response) {
                int statusCode = response.code();
              //  bannerModels = new ArrayList();
                if(statusCode==200 ){
                    System.out.println("Suree body: "+response.body());
                    ArrayList<BannerModel> bannerModel  =response.body();
                    List<SlideModel> bannerModels=new ArrayList<>();
                  //  bannerModels.add(new SlideModel(String.valueOf(Arrays.asList(bannerModel.getFile()))));
                    for (int i=0;i<bannerModel.size();i++){
                        bannerModels.add(new SlideModel(bannerModel.get(i).getFile()));
                    }
                    imageSlider.setImageList(bannerModels,false);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BannerModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notification) {
            Intent intent = new Intent(AdsHomeActivity.this,AdsNotificationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }else if (id == R.id.nav_profile) {
            Intent intent = new Intent(AdsHomeActivity.this, MyProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent d=  new Intent(AdsHomeActivity.this, Aboutus.class);
            startActivity(d);
        } else if (id == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));

        } else if (id == R.id.nav_logout) {
            logout();
        }
        else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Adrash School");
                String shareMessage= "\nAdarsh Sardarshahar download the application.\n ";
                shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
                //+ BuildConfig.APPLICATION_ID +"\n\n"
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //for logout the application
private void logout() {

    progressBarUtil.showProgress();
    ClientApi mService = ApiClient.getClient().create(ClientApi.class);
    Call<LogOut> call = mService.refCodeLogout(3, UserMobile, UserPassword, "ADS001",android_id);
    call.enqueue(new Callback<LogOut>() {
        @Override
        public void onResponse(Call<LogOut> call, Response<LogOut> response) {
            int statusCode = response.code();
            if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                if (response.body().getError()==false){
                    progressBarUtil.hideProgress();
                    SharedPrefManager.getInstance(AdsHomeActivity.this).logout();
                    startActivity(new Intent(AdsHomeActivity.this, LoginActivity.class));
                    finish();
                }else{
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                            AdsHomeActivity.this);
                    alertDialogBuilder.setTitle("Alert");
                    alertDialogBuilder
                            .setMessage(response.body().getError_Message())
                            .setCancelable(false)
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    forceLogout();
                                }
                            });

                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }


            } else {
                progressBarUtil.hideProgress();
                Toast.makeText(AdsHomeActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onFailure(Call<LogOut> call, Throwable t) {
            Toast.makeText(AdsHomeActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println(t.getLocalizedMessage());
        }
    });
}
    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

    // showing the update pop up to user
    private class GetLastesVersion extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                sLastestVersion = Jsoup
                        .connect("https://play.google.com/store/apps/details?id="+getPackageName())
                        .timeout(3000)
                        .get()
                        .select("div.hAyfc:nth-child(4)>"+"span:nth-child(2)> div:nth-child(1)"+"> span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sLastestVersion;
        }
        @Override
        protected void onPostExecute(String s) {
            sCurrentVersion = BuildConfig.VERSION_NAME;
            if (sLastestVersion !=null) {
                String ver1[] = sCurrentVersion.split("\\.");//"1.3.1"
                String ver2[] = sLastestVersion.split("\\.");//"1.3.2"
                int len1 = ver1.length;
                int len2 = ver2.length;


                for (int i = 0; i < len1; i++) {
                    if (!ver1[i].equals(ver2[i])) {
                        Log.i("log", "onPostExecute: " + ver1[i] + " " + ver2[i]);
                        version = true;
                        updateAlertDialog();
                    }
                }
            }
        }
    }

    private void updateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Set title
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("update available");
        builder.setCancelable(false);
        builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="+getPackageName())));

                //dismiss dialog
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        builder.show();
    }

    private void updateAttendenceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Set title
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(AssignmentData.AttendenceMessage);
        builder.setCancelable(false);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        builder.show();
    }
}
