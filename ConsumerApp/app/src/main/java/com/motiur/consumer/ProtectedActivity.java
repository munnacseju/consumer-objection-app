package com.motiur.consumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ProtectedActivity extends AppCompatActivity {

    private String language;
    private SharedPreferences sharedPreferences;
    private Switch mySwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected);

        mySwitch = findViewById(R.id.nightModeswithchId);

        findViewById(R.id.createObjectionId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
            }
        });

        findViewById(R.id.seeObjectionsId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AllObjectionsActivity.class));
            }
        });

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

    }


//    Change Language
    private void changeLanguage() {
        if(language.equals("bn")){
            language="ak";
//            Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        }else {
            language="bn";
//            Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        }
        Locale locale = new Locale(language.toLowerCase().trim());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences languagepref = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = languagepref.edit();
        editor.putString("language",language);
        editor.apply();
    }

//    Option Menu Creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    Option Menu Selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutMenuId){
            SharedPreferences authPref = getSharedPreferences("data",MODE_PRIVATE);
            SharedPreferences.Editor editor = authPref.edit();
            editor.putString("auth",null);
            editor.apply();
            finish();
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));

        }
        else if(item.getItemId() ==R.id.changeLanguageMenuId){
            changeLanguage();
            finish();
            startActivity(getIntent());
        }
        else if(item.getItemId()==R.id.contactUsMenuId){
            contactUsFunction();
        }
        else if(item.getItemId()==R.id.helpMenuId){
            callForDialog(R.string.help_data);
        }
        else if(item.getItemId()==R.id.shareMenuId){
            shareFunction();
        }


        return super.onOptionsItemSelected(item);
    }

    private void shareFunction() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String subject = "ConsumerObjection Apps";
//        String text = R.string.share_data;
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, R.string.share_data);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void contactUsFunction() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        String subject = "ConsumerObjection Apps";
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"motiur.munna@dynamicmalloc.xyz"});
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

//    Call For DialogInterface
    private void callForDialog(int dialogStringData){
        Dialog dialog;
        dialog = new Dialog(this);
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

    //    On Start
    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language", "bn");
    }

//    ON Back
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.want_to_exit).setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("");
        alertDialog.show();
    }
}