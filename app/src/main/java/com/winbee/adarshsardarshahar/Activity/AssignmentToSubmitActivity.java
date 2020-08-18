package com.winbee.adarshsardarshahar.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.adarshsardarshahar.Adapter.AssignmentToSubmitAdapter;
import com.winbee.adarshsardarshahar.Models.AssignmentDatum;
import com.winbee.adarshsardarshahar.Models.AssignmentToSubmit;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentToSubmitActivity extends AppCompatActivity {
    private List<AssignmentDatum> list;
    RelativeLayout today_classes;
    private ProgressBarUtil progressBarUtil;
    ScrollView scrollView;
    private AssignmentToSubmitAdapter assignmentToSubmitAdapter;
    private RecyclerView assignmentView;
    String Userid;
    private Button btn_submitted;
    private int mYear, mMonth, mDay;
    LinearLayout home,histroy,logout,layout_doubt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_to_submit);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        home=findViewById(R.id.layout_home);
        histroy=findViewById(R.id.layout_history);
        logout=findViewById(R.id.layout_logout);
        today_classes=findViewById(R.id.today_classes);
        Userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil   =  new ProgressBarUtil(this);
        assignmentView = findViewById(R.id.assignment_review);
        btn_submitted=findViewById(R.id.btn_submitted);
        btn_submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignmentToSubmitActivity.this,SubmittedAssignmentActivity.class);
                startActivity(intent);

            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignmentToSubmitActivity.this,DiscussionActivity.class);
                startActivity(intent);
            }
        });
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
                Intent intent = new Intent(AssignmentToSubmitActivity.this,AdsHomeActivity.class);
                startActivity(intent);
            }
        });

        callAllAssignment(Userid);
    }
    private void callAllAssignment(String Userid) {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<AssignmentToSubmit> call = apiCAll.getAllAssignment("WB_005",Userid);
        call.enqueue(new Callback<AssignmentToSubmit>() {
            @Override
            public void onResponse(Call<AssignmentToSubmit> call, Response<AssignmentToSubmit> response) {
                AssignmentToSubmit assignmentToSubmit=response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getAssignment() == 1) {
                        list = new ArrayList<>(Arrays.asList(assignmentToSubmit.getData()));
                        System.out.println("Suree body: " + response.body());
                        assignmentToSubmitAdapter = new AssignmentToSubmitAdapter(AssignmentToSubmitActivity.this, list);
                        assignmentView.setAdapter(assignmentToSubmitAdapter);
                        progressBarUtil.hideProgress();
                    }else{
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }
                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AssignmentToSubmit> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topic_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.topic_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQueryHint("Search Assignment");
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                datePicker();
//            }
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                datePicker();
                searchTopicByDate(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                datePicker();
                searchTopicByDate(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchTopicByDate(String s) {
        List<AssignmentDatum> filteredList = new ArrayList<>();
        try{
            for(int i=0;i<list.size();i++) {
                String Date = "" ;
               // String Date = datePicker(); ;

                if(list.get (i).getStart_date()!=null   ){
                    Date= list.get (i).getStart_date();
                }

                if(Date.toLowerCase().contains(s.toLowerCase()) ) {
                    filteredList.add(list.get(i));
                }
            }

            assignmentToSubmitAdapter = new AssignmentToSubmitAdapter(AssignmentToSubmitActivity.this, filteredList);
            assignmentView.setAdapter (assignmentToSubmitAdapter);
            assignmentToSubmitAdapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void datePicker(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //editTextDob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}
