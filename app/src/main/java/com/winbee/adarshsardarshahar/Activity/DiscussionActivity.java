package com.winbee.adarshsardarshahar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;

import java.util.Objects;

public class DiscussionActivity extends AppCompatActivity {

    TextView txtAsk,txtMcq;
    private boolean onMcqFragment=false;
    private boolean onAskFragment=true;
    private ImageView WebsiteHome,img_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        txtAsk= findViewById(R.id.txtViewAsk);
        txtMcq= findViewById(R.id.txtViewMcq);



        defalutAskTab(false);

        txtAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onAskFragment){
               defalutAskTab(true);
                onAskFragment=true;
                onMcqFragment=false;
                }
            }
        });

        txtMcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onMcqFragment) {
                    McqTab(true);
                    onMcqFragment=true;
                    onAskFragment=false;
                }
            }
        });

    }

    private void defalutAskTab(boolean addToBackStack) {


        AskFragment askFragment = new AskFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerDisscussion,askFragment,"AskFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    onAskFragment=true;
    }
    private void McqTab(boolean addToBackStack) {


        McqFragment askFragment = new McqFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerDisscussion,askFragment,"McqFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onMcqFragment=true;
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}
