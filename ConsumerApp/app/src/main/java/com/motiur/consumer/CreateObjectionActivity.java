package com.motiur.consumer;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.motiur.consumer.api.RetrofitClient;
import com.motiur.consumer.model.Objection;
import com.motiur.consumer.util.EncodeDecodeUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateObjectionActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_VIDEO_CAPTURE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Chronometer chronometer;
    private Button recorderBtn;
    private Button captureBtn;
    private Button submitBtn;
    private boolean isRecording = false;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    private MediaRecorder mediaRecorder;
    private String recordFile;
    private String imageBase64 = "";
    private String audioBase64 = "";
    private String videoBase64 = "";
    private String price = "";
    private ImageView imageView;
    private EditText etPrice;
    private Dialog dialog;
    private ProgressBar progressBar;
    private MediaPlayer mediaPlayer;

    Button captureVideoBt;
    VideoView videoView;
    int REQUEST_COD_VIDEO_CAPTURE = 2607;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private int EXTERNAL_STORAGE_PERMISSION_CODE_WRITE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_objection);

        progressBar = findViewById(R.id.progressBarId);
        recorderBtn = findViewById(R.id.recordBtn);
        chronometer = findViewById(R.id.chronometerId);
        captureBtn = findViewById(R.id.captureButtonId);
        imageView = findViewById(R.id.imageView);
        submitBtn = findViewById(R.id.submitBtn);
        etPrice = findViewById(R.id.priceEditTextId);

        videoView = findViewById(R.id.videoViewId);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        dialog = new Dialog(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){
                    recorderBtn.setText(R.string.start_record);
                    isRecording = false;
                    stopRecording();
                }
                else {
                    if(checkPermissions()){
                        recorderBtn.setText(R.string.stop_record);
                        isRecording = true;
                        startRecording();
                    }
                }
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                captureImageIntent();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidationOk = formValidation();
                if(isValidationOk){
                    objectionRequest();
                }
            }
        });

        findViewById(R.id.videoCaptureButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeVideoIntent();
            }
        });
    }

    private boolean formValidation() {
        price = etPrice.getText().toString().trim();
        if(imageBase64==""){
            imageView.requestFocus();
            Toast.makeText(getApplicationContext(), "Capture an Image First", Toast.LENGTH_SHORT).show();
            return false;
        }else if(audioBase64==""){
            recorderBtn.requestFocus();
            Toast.makeText(getApplicationContext(), "Record an Audio First", Toast.LENGTH_SHORT).show();
            return false;
        }else if(price.isEmpty()){
            etPrice.setError("Enter Price");
            etPrice.requestFocus();
            Toast.makeText(getApplicationContext(), "Enter Objection Details", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }


//    Iamge and Video Activity For Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBase64 = EncodeDecodeUtil.encodeImageToBase64(imageBitmap, this);
            Bitmap decodedImage = EncodeDecodeUtil.decodeBase64ToImage(imageBase64, this);
            imageView.setImageBitmap(decodedImage);
        }
        else if(requestCode==REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK){
            Uri videoUri = data.getData();
            videoBase64 = EncodeDecodeUtil.encodeVideoToBase64(videoUri, this);

            Uri uri = EncodeDecodeUtil.decodeBase64ToVideo(videoBase64, this);
            videoView.setVideoURI(uri);
            videoView.start();
        }
    }

    private void captureImageIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d("tag", "I am here");
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(CreateObjectionActivity.this, "photo taking failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void takeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    private void startRecording() {
        String recordPath = this.getExternalFilesDir("/").getAbsolutePath();
        recordFile = recordPath +"/" + "Recording_" + "_myfilename.mp3";
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            Toast.makeText(getApplicationContext(), "Starting Successfully", Toast.LENGTH_SHORT).show();
            mediaRecorder.prepare();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Find Exception " + e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        mediaRecorder.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();

        audioBase64 = EncodeDecodeUtil.encodeAudioToBase64(recordFile, this);
        mediaRecorder = null;
        chronometer.stop();
    }

    private boolean checkPermissions() {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), recordPermission)== PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
            Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            return false;
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
    private void objectionRequest() {

        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .sendObjection(new Objection(imageBase64, audioBase64, videoBase64, price));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("hello", call.request().toString());
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(CreateObjectionActivity.this, "Successfully objectioned data!", Toast.LENGTH_LONG).show();
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