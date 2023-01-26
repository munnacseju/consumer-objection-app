package com.motiur.consumer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.motiur.consumer.api.RetrofitClient;
import com.motiur.consumer.model.RegisterResponse;
import com.motiur.consumer.model.User;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etUsername, etName, etNationalId, etFatherName, etMotherName, etOccupation, etPresentAddress, etParmanentAddress;
    private Button registerBtn;
    private Button loginBtn;
    private TextView tvDateOfBirth;
    String dateOfBirthString;
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
        tvDateOfBirth = findViewById(R.id.dateOfBirtEt);
        etNationalId = findViewById(R.id.nationalIdEt);
        etFatherName = findViewById(R.id.fatherNameEt);
        etMotherName = findViewById(R.id.motherNameEt);
        etOccupation = findViewById(R.id.occupationEt);
        etPresentAddress = findViewById(R.id.presentAddressEt);
        etParmanentAddress = findViewById(R.id.parmanentAddressEt);

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

        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callForDatePickerDiolog();
            }
        });

    }

    private void callForDatePickerDiolog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegistrationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateOfBirthString = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        tvDateOfBirth.setText(dateOfBirthString);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void registerUser() {
        String mobileNumber = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();

        String dateOfBirth, nationalId, fatherName, motherName, occupation, presentAddress, parmanentAddress;
        dateOfBirth = tvDateOfBirth.getText().toString().trim();
        nationalId = etNationalId.getText().toString().trim();
        fatherName = etFatherName.getText().toString().trim();
        motherName = etMotherName.getText().toString().trim();
        occupation = etOccupation.getText().toString().trim();
        presentAddress = etPresentAddress.getText().toString().trim();
        parmanentAddress = etParmanentAddress.getText().toString().trim();

        Boolean isValid = checkValidation(mobileNumber, password, email, name, dateOfBirth, nationalId, fatherName, motherName, occupation, presentAddress, parmanentAddress);
        if(isValid){
            findViewById(R.id.progressBarId).setVisibility(View.VISIBLE);
            Call<RegisterResponse> call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .createUser(new User(name, email, mobileNumber, password, fatherName, motherName, dateOfBirthString, occupation, presentAddress, parmanentAddress, nationalId));

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

    private Boolean checkValidation(String mobileNumber, String password, String email, String name, String dateOfBirth, String nationalId, String fatherName, String motherName, String occupation, String presentAddress, String parmanentAddress) {
        if (email.isEmpty()) {
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return false;
        }else if (name.isEmpty()) {
            etName.setError("Mobile number is required!");
            etName.requestFocus();
            return false;
        } else if (mobileNumber.isEmpty()) {
            etUsername.setError("mobile number is required!");
            etUsername.requestFocus();
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter correct Email!");
            etEmail.requestFocus();
            return false;
        }else if( password.length()<6){
            etPassword.setError("Password is too short!");
            etPassword.requestFocus();
            return false;
        }else if (dateOfBirth.isEmpty()) {
            tvDateOfBirth.setError("Date of birth is required!");
            tvDateOfBirth.requestFocus();
            return false;
        }else if (nationalId.isEmpty()) {
            etNationalId.setError("Nation id is required!");
            etNationalId.requestFocus();
            return false;
        }else if (fatherName.isEmpty()) {
            etFatherName.setError("Father's na is required!");
            etFatherName.requestFocus();
            return false;
        }else if (motherName.isEmpty()) {
            etMotherName.setError("Mother's name is required!");
            etMotherName.requestFocus();
            return false;
        }else if (occupation.isEmpty()) {
            etOccupation.setError("Occupation is required!");
            etOccupation.requestFocus();
            return false;
        }else if (presentAddress.isEmpty()) {
            etPresentAddress.setError("Present address is required!");
            etPresentAddress.requestFocus();
            return false;
        }else if (parmanentAddress.isEmpty()) {
            etParmanentAddress.setError("Parmanent address is required!");
            etParmanentAddress.requestFocus();
            return false;
        }else{
            return true;
        }
    }
}