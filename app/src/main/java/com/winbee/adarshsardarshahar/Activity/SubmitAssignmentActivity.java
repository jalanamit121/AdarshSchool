package com.winbee.adarshsardarshahar.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.winbee.adarshsardarshahar.Models.AssignmentDatum;
import com.winbee.adarshsardarshahar.Models.SubmitAssignment;
import com.winbee.adarshsardarshahar.R;
import com.winbee.adarshsardarshahar.RetrofitApiCall.ApiClient;
import com.winbee.adarshsardarshahar.Utils.AssignmentData;
import com.winbee.adarshsardarshahar.Utils.ProgressBarUtil;
import com.winbee.adarshsardarshahar.Utils.SharedPrefManager;
import com.winbee.adarshsardarshahar.WebApi.ClientApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitAssignmentActivity extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;
    boolean isOnlyImageAllowed = false;
    private Uri filePath;
    private EditText description;
    private Button uploadButton,addImageButton;
    private TextView responseTextView;
    String UserId;
    private ProgressBarUtil progressBarUtil;
    AssignmentDatum assignmentDatum;
    private  static final int IMG_REQUEST=777;
    private Bitmap bitmap;
    ImageView image_view;
    LinearLayout layout_description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        description = findViewById(R.id.description);
        addImageButton = findViewById(R.id.addImageButton);
        uploadButton = findViewById(R.id.uploadButton);
        image_view = findViewById(R.id.image_view);
        layout_description = findViewById(R.id.layout_description);
        progressBarUtil = new ProgressBarUtil(this);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        final String AssignmentId=getIntent().getStringExtra("assignmentId");
        final String  CourseId=getIntent().getStringExtra("courseId");

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                fileValidation();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            assignmentDatum = (AssignmentDatum) bundle.getSerializable("assignmentDatum");


        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fileValidation() {
        String Description = description.getText().toString();
        String Image =imageToString();


        //validating inputs
        if (TextUtils.isEmpty(Description)) {
            description.setError("Please enter your answer");
            description.requestFocus();
            return;
        }

        callSubmitAssignment(Description,Image);
    }
    private void callSubmitAssignment(String Description,String Image){
        // File pdfFile = new File(String.valueOf(filePath));

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<SubmitAssignment> call = mService.getSubmitAssignment("WB_005",UserId, AssignmentData.BucketId,AssignmentData.AssignmentId,Image,Description);
        call.enqueue(new Callback<SubmitAssignment>() {
            @Override
            public void onResponse(Call<SubmitAssignment> call, Response<SubmitAssignment> response) {
                int statusCode  = response.code();
                if(statusCode==200  ) {
                    if (response.body().getSubmitted()!=null) {
                        progressBarUtil.hideProgress();
                        Intent intent = new Intent(SubmitAssignmentActivity.this, AdsHomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(SubmitAssignmentActivity.this, "Submit Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        progressBarUtil.hideProgress();
                        Toast.makeText(SubmitAssignmentActivity.this, "Assignment Not Submitted", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(SubmitAssignmentActivity.this, "SomeThing went wrong ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SubmitAssignment> call, Throwable t) {
                Toast.makeText(SubmitAssignmentActivity.this,"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                image_view.setImageBitmap(bitmap);

                Toast.makeText(this, "File Selected", Toast.LENGTH_SHORT).show();
                addImageButton.setVisibility(View.GONE);
                uploadButton.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//
//            addImageButton.setVisibility(View.GONE);
//            uploadButton.setVisibility(View.VISIBLE);
//        }
    }
    //    @RequiresApi(api = Build.VERSION_CODES.O)
    private String imageToString()
    {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        File pdfFile = new File(String.valueOf(filePath));
//
//        byte[] encoded = new byte[0];
//        try {
//            encoded = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}