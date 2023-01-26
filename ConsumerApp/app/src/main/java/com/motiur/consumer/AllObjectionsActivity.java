package com.motiur.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motiur.consumer.Adapter.MyListAdapter;
import com.motiur.consumer.api.ConsumerObjectionClient;
import com.motiur.consumer.model.GetAllObjectionResponse;
import com.motiur.consumer.model.Objection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllObjectionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_objections);

        progressBar = findViewById(R.id.progressBarId);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getObjections();
    }

    @Override
    protected void onStart() {
        getObjections();
        super.onStart();
    }

    private void getObjections() {
        progressBar.setVisibility(View.VISIBLE);
        Call<GetAllObjectionResponse> call = ConsumerObjectionClient
                .getInstance()
                .getAPI()
                .getObjections();

        call.enqueue(new Callback<GetAllObjectionResponse>() {
            @Override
            public void onResponse(Call<GetAllObjectionResponse> call, Response<GetAllObjectionResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    progressBar.setVisibility(View.GONE);
                    String message = response.body().getMessage();
//                    Toast.makeText(getApplicationContext(), "Successfully got data! "+message, Toast.LENGTH_LONG).show();
                    ArrayList<Objection>dataList = response.body().getData();
                    if (dataList.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Sorry, you have no objection!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ProtectedActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        String value = dataList.get(0).getImageBase64();
                        Log.d("", value);
                        MyListAdapter adapter = new MyListAdapter(dataList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllObjectionResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
            //MediaPlayer mp = MediaPlayer.create(getApplicationContext(), Uri.parse("/storage/sdcard0/Android/data/com.somename.myapp/files/beach.mp3"));

        }
        return super.onOptionsItemSelected(item);
    }

}