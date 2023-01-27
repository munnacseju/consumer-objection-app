package com.motiur.consumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.motiur.consumer.api.ConsumerObjectionClient;
import com.motiur.consumer.constants.ConsumerConstant;
import com.motiur.consumer.model.GetObjectionResponse;
import com.motiur.consumer.model.Objection;
import com.motiur.consumer.util.EncodeDecodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectionActivity extends AppCompatActivity {
    ImageView imageView, audioView;
    VideoView videoView;
    TextView textView;
    Button deleteObjectionButton;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    MediaPlayer audioMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Long objectionId = getIntent().getLongExtra("objectionId", 1L);

        deleteObjectionButton = findViewById(R.id.deleteObjectionId);
        imageView = findViewById(R.id.objectionImageViewId);
        audioView = findViewById(R.id.objectionAudioId);
        videoView = findViewById(R.id.objectionVideoViewId);
        textView = findViewById(R.id.objectionTextViewId);
        progressBar = findViewById(R.id.progressBarId);
        linearLayout = findViewById(R.id.linearLayoutId);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        requestForGetObjection(objectionId);


        deleteObjectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestForDeleteaObjection(objectionId);
            }

        });

        audioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(audioMediaPlayer.isPlaying()){
                    audioView.setImageResource(R.drawable.play_foreground);
                    audioMediaPlayer.pause();
    //                    audioMediaPlayer.stop();
                    Toast.makeText(getApplicationContext(), "Stopping", Toast.LENGTH_LONG).show();
                }else{
                    audioView.setImageResource(R.drawable.pause_foreground);
                    audioMediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Starting", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void requestForGetObjection(Long objectionId) {
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        Call<GetObjectionResponse> call = ConsumerObjectionClient
                .getInstance()
                .getAPI()
                .getObjection(objectionId);

        call.enqueue(new Callback<GetObjectionResponse>() {
            @Override
            public void onResponse(Call<GetObjectionResponse> call, Response<GetObjectionResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
//                    Toast.makeText(getApplicationContext(), "Successfully got data! "+response.body(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    GetObjectionResponse objectionResponse = response.body();
                    if(objectionResponse.getStatus().equals(ConsumerConstant.STATUS.OK.toString())) {

                        Objection objection = objectionResponse.getObjection();

                        String imageData = objection.getImageBase64();
                        String videoData = objection.getVideoBase64();
                        String audidioData = objection.getAudioBase64();
                        String objectionDetails = objection.getObjectionDetails();
                        Toast.makeText(ObjectionActivity.this, objectionResponse.getMessage() + "   " + objectionDetails + " detials and status: " + objectionResponse.getStatus(), Toast.LENGTH_SHORT).show();

                        imageView.setImageBitmap(EncodeDecodeUtil.decodeBase64ToImage(imageData, getApplicationContext()));
                        videoView.setVideoURI(EncodeDecodeUtil.decodeBase64ToVideo(videoData,getApplicationContext()));
                        videoView.start();
                        textView.setText("Price = " + objectionDetails + " Updated");

//                        Uri audioUri = EncodeDecodeUtil.decodeBase64ToAudio(audidioData, getApplicationContext());// initialize Uri here
//                        audioMediaPlayer = MediaPlayer.create(getApplicationContext(), audioUri);
                    }else{
                        Toast.makeText(ObjectionActivity.this, objectionResponse.getMessage() + " Oho!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Some unknown problem occurred from objection!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetObjectionResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestForDeleteaObjection(Long objectionId) {
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ConsumerObjectionClient
                .getInstance()
                .getAPI()
                .deleteObjection(objectionId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if(response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject json = new JSONObject(response.body().string());
//                            JSONObject data = (JSONObject) json.get("data");
                            String statusCode = (String) json.get("status");
                            String message = (String) json.get("message");
                            Toast.makeText(getApplicationContext(), message + " status code :  " + statusCode, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), AllObjectionsActivity.class);
                            finish();
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Some unknown problem occurred from objection!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}