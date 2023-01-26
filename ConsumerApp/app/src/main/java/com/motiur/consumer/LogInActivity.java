package com.motiur.consumer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.motiur.consumer.api.RetrofitClient;
import com.motiur.consumer.model.User;
import com.motiur.consumer.security.AuthToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button regirstrationActivityButton;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginBtn = findViewById(R.id.loginButtonid);
        etEmail = findViewById(R.id.emailEditTextid);
        etPassword = findViewById(R.id.passwordEditTextid);
        regirstrationActivityButton = findViewById(R.id.regirstrationActivityButtonid);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        regirstrationActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        } else if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter correct Email");
            etEmail.requestFocus();
            return;
        }else if( password.length()<6){
            etPassword.setError("Password is too short");
            etPassword.requestFocus();
            return;
        }
        findViewById(R.id.progressBarId).setVisibility(View.VISIBLE);
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .checkUser(new User(email, password));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                findViewById(R.id.progressBarId).setVisibility(View.GONE);
//                String s = "ha ha hi hi";
//                Log.d("hello", call.request().toString());
                if (response.isSuccessful() && response.code() == 200) {
                    if(response.headers().get("Authorization")!=null){

                        Log.d("token", response.headers().get("Authorization"));

                        String authToken = response.headers().get("Authorization");
                        AuthToken.authToken = authToken;
                        Toast.makeText(LogInActivity.this, "Successfully Logged in!", Toast.LENGTH_LONG).show();


                        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("auth", authToken);
                        editor.apply();
                        finish();
                        startActivity(new Intent(LogInActivity.this, ProtectedActivity.class));
                    }else{
                        Toast.makeText(LogInActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogInActivity.this, "Wrong Credentials! Try again!", Toast.LENGTH_LONG).show();
//                    Toast.makeText(Registration.this, "Some unknown problem occurred!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                findViewById(R.id.progressBarId).setVisibility(View.GONE);
                Toast.makeText(LogInActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());
            }
        });

    }
}