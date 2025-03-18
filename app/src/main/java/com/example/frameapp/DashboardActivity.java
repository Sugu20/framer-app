package com.example.frameapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frameapp.R;
import com.example.frameapp.api.ApiService;
import com.example.frameapp.response.UserResponse;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends AppCompatActivity {

    private ImageView profileImage;
    private String BASE_URL = "https://yourdomain.com/"; // Change to your actual API domain

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        profileImage = findViewById(R.id.profileImage);

        // Fetch stored phone number
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phone_number", null);

        if (phoneNumber != null) {
            fetchUserProfile(phoneNumber);
        }
    }

    private void fetchUserProfile(String phoneNumber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponse> call = apiService.getUser(phoneNumber);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    String imageUrl = BASE_URL + response.body().getUser().getImageUrl();

                    // Load Image into ImageView using Picasso
                    Picasso.get().load(imageUrl).placeholder(R.drawable.profile_placeholder).into(profileImage);
                } else {
                    Toast.makeText(DashboardActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
