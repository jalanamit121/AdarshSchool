package com.winbee.adarshsardarshahar.Activity;

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

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.winbee.adarshsardarshahar.Adapter.AdsHomeAdapter;
import com.winbee.adarshsardarshahar.Adapter.AdsHomeLiveAdapter;
import com.winbee.adarshsardarshahar.BuildConfig;
import com.winbee.adarshsardarshahar.Models.AttendenceModel;
import com.winbee.adarshsardarshahar.Models.BannerModel;
import com.winbee.adarshsardarshahar.Models.BranchName;
import com.winbee.adarshsardarshahar.Models.CourseDatum;
import com.winbee.adarshsardarshahar.Models.LiveClass;
import com.winbee.adarshsardarshahar.Models.PurchasedMainModel;
import com.winbee.adarshsardarshahar.Models.RefCode;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.LinePagerIndicatorDecoration;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import org.jsoup.Jsoup;

import java.io.IOException;
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
    private ArrayList<BranchName> list1;
    private ArrayList<LiveClass> liveList;
    private RecyclerView video_list_recycler,video_list_recycler1;
    private AdsHomeAdapter adapter;
    private AdsHomeLiveAdapter liveAdapter;
    ScrollView scrollView;
    RelativeLayout home,histroy,logout,layout_noCourse,layout_welcome,layout_myClass,layout_online_test,layout_assignment,layout_attendence;
    private ArrayList<RefCode> refCodeData;
    private ProgressBarUtil progressBarUtil;
    boolean doubleBackToExitPressedOnce = false;
    TextView viewAll,nocourse,noclasses;
    String sCurrentVersion,sLastestVersion,Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_home);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
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



        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        Userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        nocourse=findViewById(R.id.nocourse);
        noclasses=findViewById(R.id.noclasses);
        layout_online_test=findViewById(R.id.layout_online_test);
        layout_assignment=findViewById(R.id.layout_assignment);
        layout_noCourse=findViewById(R.id.layout_noCourse);
        layout_welcome=findViewById(R.id.layout_welcome);
        layout_myClass=findViewById(R.id.layout_myClass);
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
               //api call
                callAttendenceService();
            }
        });
//        layout_myClass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(GecHomeActivity.this,MyClassActivity.class);
//                startActivity(intent);
//            }
//        });
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
        layout_online_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsHomeActivity.this,SubjectActivity.class);
                startActivity(intent);
            }
        });

        ImageSlider imageSlider =(ImageSlider)findViewById(R.id.slider);

        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1));
        slideModels.add(new SlideModel(R.drawable.banner2));
        slideModels.add(new SlideModel(R.drawable.banner3));
        slideModels.add(new SlideModel(R.drawable.banner4));
        slideModels.add(new SlideModel(R.drawable.banner5));
        imageSlider.setImageList(slideModels,true);
        //callBannerService();
        callApiService(Userid);
        callLiveApiService();



    }

    private void callLiveApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<LiveClass>> call = apiCAll.getLive(3,"wb_005",Userid);
        call.enqueue(new Callback<ArrayList<LiveClass>>() {
            @Override
            public void onResponse(Call<ArrayList<LiveClass>> call, Response<ArrayList<LiveClass>> response) {
                int statusCode = response.code();
                liveList = new ArrayList();
                if(statusCode==200) {
                    if (response.body().size()!=0) {
                        System.out.println("Suree body: " + response.body());
                        liveList = response.body();
                        liveAdapter = new AdsHomeLiveAdapter(AdsHomeActivity.this, liveList);
                        video_list_recycler1.setAdapter(liveAdapter);
                        progressBarUtil.hideProgress();
                    }else{
                        progressBarUtil.hideProgress();
                        noclasses.setVisibility(View.VISIBLE);
                    }
                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LiveClass>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    //for attendeence
    private void callAttendenceService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<AttendenceModel> call = apiCAll.fetchAttendence(Userid);
        call.enqueue(new Callback<AttendenceModel>() {
            @Override
            public void onResponse(Call<AttendenceModel> call, Response<AttendenceModel> response) {
                int statusCode = response.code();
                liveList = new ArrayList();
                if(statusCode==200) {
                    AssignmentData.AttendenceMessage=response.body().getMessage();
                    updateAttendenceDialog();
                    progressBarUtil.hideProgress();
                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Network Issues" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AttendenceModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }


    private void callApiService(String Userid) {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<PurchasedMainModel> call = apiCAll.getCourseById(1,Userid,"WB_005");
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
                            list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(purchasedMainModel).getData()));
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
//    private void callBannerService() {
//        progressBarUtil.showProgress();
//        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
//        Call<ArrayList<BannerModel>> call = apiCAll.getBanner("WB_004");
//        call.enqueue(new Callback<ArrayList<BannerModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<BannerModel>> call, Response<ArrayList<BannerModel>> response) {
//                int statusCode = response.code();
//              //  bannerModels = new ArrayList();
//                if(statusCode==200 && response.body().size()!=0){
//                    System.out.println("Suree body: "+response.body().get(0));
//
//                    List<SlideModel> bannerModels=new ArrayList<>();
//                    bannerModels.add(new SlideModel(response.body().indexOf(0)));
//                   // bannerModels.add(new SlideModel("https://s3.amazonaws.com/influencive.com/wp-content/uploads/2020/01/30131217/pexels-photo-2312369-e1580418773326.jpeg"));
//                    //imageSlider.setImageList(bannerModels,true);
//                    progressBarUtil.hideProgress();
//                }
//                else{
//                    System.out.println("Suree: response code"+response.message());
//                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<BannerModel>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();
//
//                System.out.println("Suree: Error "+t.getMessage());
//            }
//        });
//    }

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

//        }else if (id == R.id.nav_online_test) {
//        Intent intent = new Intent(GecHomeActivity.this,SubjectActivity.class);
//        startActivity(intent);
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

    private void logout() {
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
            if (sLastestVersion !=null){
                float cVersion = Float.parseFloat(sCurrentVersion);
                float lVersion = Float.parseFloat(sLastestVersion);
                // check the condition whether the lastet version is greater than current version
                if (lVersion>cVersion){
                    // create update alert dilog box
                    updateAlertDialog();
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
