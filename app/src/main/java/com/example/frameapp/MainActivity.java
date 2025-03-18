package com.example.frameapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Your XML layout file

        // Handler to delay transition to LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After the delay, navigate to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Optional: finish MainActivity to prevent it from being in the back stack
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
