package com.winbee.adarshsardarshahar.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.winbee.adarshsardarshahar.Models.RefUser;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.LocalData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SpinnerAdapter;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity  {

    EditText editTextname, editTextEmail, editTextPassword,editTextPhone,editTextRePassword,editTextFatherName;
    TextView editTextReferalCode,txt_terms;
    Button register;
    Spinner spinner_std;
    long item;

    private ProgressBarUtil progressBarUtil;
    ArrayList<String> listState=new ArrayList<String>();
    ArrayList<String> listCity=new ArrayList<String>();
    AutoCompleteTextView act1,act2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        callAll();
        initView();
    }
    public void callAll()
    {
        obj_list();
        addState();
        //addCity();
    }
    // Get the content of cities.json from assets directory and store it as string
    public String getJson()
    {
        String json=null;
        try
        {
            // Opening cities.json file
            InputStream is = getAssets().open("cities.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    // This add all JSON object's data to the respective lists
    void obj_list()
    {
        // Exceptions are returned by JSONObject when the object cannot be created
        try
        {
            // Convert the string returned to a JSON object
            JSONObject jsonObject=new JSONObject(getJson());
            // Get Json array
            JSONArray array=jsonObject.getJSONArray("array");
            // Navigate through an array item one by one
            for(int i=0;i<array.length();i++)
            {
                // select the particular JSON data
                JSONObject object=array.getJSONObject(i);
                String city=object.getString("name");
                String state=object.getString("state");
                listCity.add(city);
                listState.add(state);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    // The second auto complete text view
    void addCity()
    {
        ArrayList<String> temparray=new ArrayList<String>();
        act1=(AutoCompleteTextView)findViewById(R.id.actCity);
        for(int i=0; i<listCity.size();i++){
            if (listState.get(i).equalsIgnoreCase(act2.getText().toString())){
                temparray.add(listCity.get(i));
            }
        }
        adapterSettings(temparray);
    }

    // The third auto complete text view
    void addState()
    {
        Set<String> set = new HashSet<String>(listState);
        act2=(AutoCompleteTextView)findViewById(R.id.actState);
        adapterSetting(new ArrayList(set));
    }

    // setting adapter for auto complete text views
    void adapterSetting(ArrayList arrayList)
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
        act2.setAdapter(adapter);
        hideKeyBoard();
    }
    void adapterSettings(ArrayList arrayList)
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
        act1.setAdapter(adapter);
        hideKeyBoard1();
    }

    // hide keyboard on selecting a suggestion
    public void hideKeyBoard()
    {
        act2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                addCity();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

    }
    public void hideKeyBoard1()
    {
        act1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //andExoPlayerView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //andExoPlayerView.restoreState(savedInstanceState);
    }

    private void initView() {
        progressBarUtil   =  new ProgressBarUtil(this);
        editTextname =  findViewById(R.id.editTextUsername);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextPassword =  findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        spinner_std = findViewById(R.id.spinner_std);
        editTextRePassword  = findViewById(R.id.editTextre_Password);
        editTextFatherName  = findViewById(R.id.editTextFatherName);
        editTextReferalCode  = findViewById(R.id.editTextreferal_code);

        txt_terms  = findViewById(R.id.txt_terms);
        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,TermsActivity.class);
                startActivity(intent);
            }
        });

        String[] titleArray = getResources ( ).getStringArray ( R.array.std);
        SpinnerAdapter adapter = new SpinnerAdapter( RegisterActivity.this , titleArray);
        // adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_std.setAdapter ( adapter );
                String valToSet =  spinner_std.getSelectedItem().toString();
//        spinner_std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                item = spinner_std.getItemIdAtPosition(position);
//                System.out.println("Suree: "+item);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//        });
        CheckBox chk = (CheckBox) findViewById(R.id.chk1);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    register=(Button)findViewById(R.id.buttonRegister);
                    register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userValidation();
                        }
                    });
                }
                else{
                    register=(Button)findViewById(R.id.buttonRegister);
                    register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(RegisterActivity.this, "please Agree Terms and Conditions",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }
    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_btn){

            if(editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

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
    public void ReShowHidePass(View view){

        if(view.getId()==R.id.show_repass_btn){

            if(editTextRePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                (( ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Show Password
                editTextRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Hide Password
                editTextRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
    private void userValidation() {
        final String username = editTextname.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String fathername = editTextFatherName.getText().toString().trim();
        final String re_password = editTextRePassword.getText().toString().trim();
        final String referal_code = editTextReferalCode.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String state = act2.getText().toString().trim();
        final String city = act1.getText().toString().trim();
        final Object class_std=  spinner_std.getSelectedItem();
        if (TextUtils.isEmpty(username)) {
            editTextname.setError("Please enter username");
            editTextname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            editTextPhone.setError("Please enter your mobile number");
            editTextPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fathername)) {
            editTextFatherName.setError("Please enter father name");
            editTextFatherName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(re_password)) {
            editTextRePassword.setError("Enter a password");
            editTextRePassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(state)) {
            act2.setError("Enter State");
            act2.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            act1.setError("Enter City");
            act1.requestFocus();
            return;
        }
        if (password.equals(re_password)) {

        }else{
            editTextRePassword.setError("Password are not matching");
            editTextRePassword.requestFocus();
            return;
        }
        RefUser refUser = new RefUser();
        refUser.setName(username);
        refUser.setPassword(password);
        refUser.setEmail(email);
        refUser.setMobile(phone);
        refUser.setState(state);
        refUser.setDistrict(city);
        refUser.setRefcode(referal_code);
        refUser.setFather_name(fathername);
        refUser.setClass_for_reg(String.valueOf(class_std));


        CallSignupApi(refUser);
    }
    private void CallSignupApi(final RefUser refUser) {
        progressBarUtil.showProgress();
       ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefUser> call = mService.refUserSignIn(2, refUser.getName(),refUser.getFather_name(),
                refUser.getClass_for_reg(),refUser.getEmail(),refUser.getMobile(),
                refUser.getRefcode(),refUser.getState(),refUser.getDistrict(),refUser.getPassword());
        Log.i("tag", "CallSignupApi: "+refUser.getFather_name()+refUser.getClass_for_reg());
        call.enqueue(new Callback<RefUser>() {
            @Override
            public void onResponse(Call<RefUser> call, Response<RefUser> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getRegistration_id() != null) {
                        progressBarUtil.hideProgress();
                    Intent intent = new Intent(RegisterActivity.this, OtpVerficationActivity.class);
                    intent.putExtra("message",refUser.getMobile());
                    LocalData.UserName = refUser.getEmail();
                    LocalData.Mobile=refUser.getMobile();
                    LocalData.Password = refUser.getPassword();
                    startActivity(intent);
                    } else {
                        progressBarUtil.hideProgress();
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }


            @Override
            public void onFailure(Call<RefUser> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(RegisterActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}