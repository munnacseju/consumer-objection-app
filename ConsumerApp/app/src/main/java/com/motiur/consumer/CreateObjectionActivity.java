package com.motiur.consumer;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.motiur.consumer.api.ConsumerObjectionClient;
import com.motiur.consumer.model.Objection;
import com.motiur.consumer.util.EncodeDecodeUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateObjectionActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_VIDEO_PICK = 2;
    static final int REQUEST_IMAGE_PICK = 1;
    static final int REQUET_AUDIO_PICK = 3;
    private Button selectAudioBtn, selectVideoBtn, selectImageBtn, submitBtn;
    private String imageBase64 = "";
    private String audioBase64 = "";
    private String videoBase64 = "";
    private ImageView imageView;
    private EditText etObjectionDetials, etComplainType, etAccusedOrganizationName, etAccusedOrganizationAddress, etSignature;
    private Dialog dialog;
    private ProgressBar progressBar;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_objection);

        progressBar = findViewById(R.id.progressBarId);
        selectAudioBtn = findViewById(R.id.recordBtn);
        selectImageBtn = findViewById(R.id.captureButtonId);
        imageView = findViewById(R.id.imageView);
        submitBtn = findViewById(R.id.submitBtn);
        etObjectionDetials = findViewById(R.id.objectionDetailsEditTextId);
        selectVideoBtn =   findViewById(R.id.videoCaptureButtonId);
        etComplainType = findViewById(R.id.complainTypeEtId);
        etAccusedOrganizationName = findViewById(R.id.accusedOrganizationNameEtId);
        etAccusedOrganizationAddress = findViewById(R.id.accusedOrganizationAddressEtId);
        etSignature = findViewById(R.id.signatureEtId);

        videoView = findViewById(R.id.videoViewId);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        dialog = new Dialog(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        selectAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAudioIntent();
            }
        });

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                selectImageIntent();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidatedForAttachedFile = checkValidation();
                String complainType = etComplainType.getText().toString().trim();
                String accusedOrganizationName = etAccusedOrganizationName.getText().toString().trim();
                String accusedOrganizationAddress = etAccusedOrganizationAddress.getText().toString().trim();
                String signature = etSignature.getText().toString().trim();
                String objectionDetails = etObjectionDetials.getText().toString().trim();

                Objection objection = new Objection(imageBase64, videoBase64, audioBase64, objectionDetails, complainType, accusedOrganizationName, accusedOrganizationAddress);

                boolean isValidatedForm = checkValidationForm( complainType, accusedOrganizationName, accusedOrganizationAddress, signature, objectionDetails);
                if(isValidatedForAttachedFile && isValidatedForm){
                    objectionRequest(objection);
                }
            }
        });

        selectVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVideoIntent();
            }
        });
    }

    private boolean checkValidationForm(String complainType, String accusedOrganizationName, String accusedOrganizationAddress, String signature, String objectionDetails) {
        if(complainType.isEmpty()){
            etComplainType.setError("Enter Complain type");
            etComplainType.requestFocus();
            return false;
        }else if(accusedOrganizationName.isEmpty()){
            etAccusedOrganizationName.setError("Enter Accuesed Organization name!");
            etAccusedOrganizationName.requestFocus();
            return false;
        }else if(accusedOrganizationAddress.isEmpty()){
            etAccusedOrganizationAddress.setError("Enter Accuesed Organization address!");
            etAccusedOrganizationAddress.requestFocus();
            return false;
        }else if(signature.isEmpty()){
            etSignature.setError("Enter signature!");
            etSignature.requestFocus();
            return false;
        } else if(objectionDetails.isEmpty()){
            etObjectionDetials.setError("Enter objection detiails");
            etObjectionDetials.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkValidation() {
        if(imageBase64=="" && audioBase64 =="" && videoBase64 == ""){
            imageView.requestFocus();
            Toast.makeText(getApplicationContext(), "Select a image or video or audio first", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }


//    Iamge and Video Activity For Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageBase64 = EncodeDecodeUtil.encodeImageToBase64(imageBitmap, this);
                Bitmap decodedImage = EncodeDecodeUtil.decodeBase64ToImage(imageBase64, this);
                imageView.setImageBitmap(decodedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode== REQUEST_VIDEO_PICK && resultCode == RESULT_OK){
            Uri videoUri = data.getData();
            videoBase64 = EncodeDecodeUtil.encodeVideoToBase64(videoUri, this);
            Toast.makeText(this, "Video base 64" + videoBase64, Toast.LENGTH_SHORT).show();
            Uri uri = EncodeDecodeUtil.decodeBase64ToVideo(videoBase64, this);
            videoView.setVideoURI(uri);
            videoView.start();
        }else if (requestCode == REQUET_AUDIO_PICK && resultCode == RESULT_OK && data != null) {
            Uri videoUri = data.getData();
            audioBase64 = EncodeDecodeUtil.encodeVideoToBase64(videoUri, this);
        }else{
            Toast.makeText(this, "Someting issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_PICK);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(CreateObjectionActivity.this, "photo loading failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectVideoIntent() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select video"), REQUEST_VIDEO_PICK);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(CreateObjectionActivity.this, "video loading failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectAudioIntent() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select audio"), REQUET_AUDIO_PICK);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(CreateObjectionActivity.this, "audio loading failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.captureButtonIdInfo){
            Toast.makeText(getApplicationContext(), "Capture", Toast.LENGTH_SHORT).show();
            callForDialog("Capture .................OK.....Ok");
        }else if(v.getId()==R.id.recordBtnInfo){
            Toast.makeText(getApplicationContext(), "Record", Toast.LENGTH_SHORT).show();
            callForDialog("Record");
        }else if(v.getId()==R.id.submitBtnInfo){
            Toast.makeText(getApplicationContext(), "Submit Btn", Toast.LENGTH_SHORT).show();
            callForDialog("Submit");
        }else if(v.getId()==R.id.createVideoButtonIdInfo){
            Toast.makeText(getApplicationContext(), "Video Btn", Toast.LENGTH_SHORT).show();
            callForDialog("You are Trying for Video Creation Process");
        }
    }

    private void callForDialog(String dialogStringData){
        dialog.setContentView(R.layout.sample_info_layout);
        TextView cancell = dialog.findViewById(R.id.cancelId);
        TextView dialogText = dialog.findViewById(R.id.dialogTextId);
        dialogText.setText(dialogStringData);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //    Server Request
    private void objectionRequest(Objection objection) {

        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ConsumerObjectionClient
                .getInstance()
                .getAPI()
                .sendObjection(objection);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("hello", call.request().toString());
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(CreateObjectionActivity.this, "Successfully objection compleated!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CreateObjectionActivity.this, "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateObjectionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());
            }
        });
    }
}