package com.motiur.consumer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.motiur.consumer.api.RetrofitClient;
import com.motiur.consumer.model.RegisterResponse;
import com.motiur.consumer.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etUsername, etName;
    private Button registerBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etEmail = findViewById(R.id.emailEditTextid);
        etPassword = findViewById(R.id.passwordEditTextid);
        etUsername = findViewById(R.id.phoneNumberEditTextid);
        etName = findViewById(R.id.nameEditTextid);
        registerBtn = findViewById(R.id.registrationButtonid);
        loginBtn = findViewById(R.id.loginActivityButtonid);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LogInActivity.class));
            }
        });
    }

    private void registerUser() {
        String mobileNumber = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        } else if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }else if (name.isEmpty()) {
            etName.setError("Mobile number is required");
            etName.requestFocus();
            return;
        } else if (mobileNumber.isEmpty()) {
            etUsername.setError("Password is required");
            etUsername.requestFocus();
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
        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getAPI()
                .createUser(new User(name, email, password, mobileNumber));

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                findViewById(R.id.progressBarId).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("OK")) {
                        Toast.makeText(RegistrationActivity.this, "Successfully registered. Please login", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, LogInActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "User already exists!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Some unknown problem occurred!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                findViewById(R.id.progressBarId).setVisibility(View.GONE);
                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());
            }
        });

    }
}