package com.example.frameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frameapp.api.ApiService;
import com.example.frameapp.api.RetrofitClient;
import com.example.frameapp.response.CommonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetpasswordActivity extends AppCompatActivity {

    private EditText etPhoneNumber, etNewPassword, etConfirmPassword;
    private Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhoneNumber.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (phone.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ForgetpasswordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ForgetpasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                resetPassword(phone, newPassword);
            }
        });
    }

    private void resetPassword(String phone, String newPassword) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<CommonResponse> call = apiService.resetPassword(phone, newPassword);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ForgetpasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus().equals("success")) {
                        startActivity(new Intent(ForgetpasswordActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(ForgetpasswordActivity.this, "Error resetting password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(ForgetpasswordActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
