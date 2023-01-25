package com.motiur.consumer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.motiur.consumer.security.AuthToken;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button forRegistrationButton;
    private Button forLogInId;
    private String language="bn";
    private String auth;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        setTheme(R.style.Theme_MaterialComponents_DayNight);

        forRegistrationButton = findViewById(R.id.forRegistrationId);
        forLogInId = findViewById(R.id.forLogInId);



        forRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        forLogInId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language", "bn");
        auth = sharedPreferences.getString("auth", null);
        if(auth!=null){
            AuthToken.authToken = auth;
            checkLanguage();
            finish();
            overridePendingTransition( 0, 0);
            startActivity(new Intent(getApplicationContext(), ProtectedActivity.class));
            overridePendingTransition( 0, 0);

        }
    }

    private void checkLanguage() {
        Locale locale = new Locale(language.toLowerCase().trim());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

}