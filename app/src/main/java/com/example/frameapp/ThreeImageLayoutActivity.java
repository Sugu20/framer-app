package com.example.frameapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

public class ThreeImageLayoutActivity extends AppCompatActivity {

    private boolean isGridLayout = true; // Default layout is Grid
    private Button toggleButton, chooseFrameButton;
    private ImageView imageTop, imageBottomLeft, imageBottomRight;
    private ArrayList<String> imageUris = new ArrayList<>(); // List to store received image URIs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_image_layout);

        // Initialize Views
        toggleButton = findViewById(R.id.toggleButton);
        chooseFrameButton = findViewById(R.id.chooseFrameButton);

        // Image Views
        imageTop = findViewById(R.id.imageTop);
        imageBottomLeft = findViewById(R.id.imageBottomLeft);
        imageBottomRight = findViewById(R.id.imageBottomRight);

        // Retrieve images from Intent
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> receivedImages = intent.getStringArrayListExtra("selectedImages");
            if (receivedImages != null && receivedImages.size() >= 3) {
                imageUris.addAll(receivedImages);
            }
        }

        // Debugging Logs
        Log.d("ThreeImageLayoutActivity", "Received Images Count: " + imageUris.size());
        if (!imageUris.isEmpty()) {
            for (int i = 0; i < imageUris.size(); i++) {
                Log.d("ThreeImageLayoutActivity", "Image " + i + ": " + imageUris.get(i));
            }
        }

        // Check if images are valid
        if (imageUris.size() < 3) {
            Log.e("ThreeImageLayoutActivity", "Not enough images received.");
            Toast.makeText(this, "Please select at least three images.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if images are not valid
            return;
        }

        loadImages(); // Load images into views

        // Toggle Layout Button Click Listener
        toggleButton.setOnClickListener(v -> toggleLayout());

        // Choose Frame Button Click Listener
        chooseFrameButton.setOnClickListener(v -> sendSelectedLayout());
    }

    private void loadImages() {
        if (imageUris.size() < 3) {
            Log.e("ThreeImageLayoutActivity", "Error: Image list is invalid.");
            return;
        }

        try {
            Glide.with(this).load(Uri.parse(imageUris.get(0))).into(imageTop);
            Glide.with(this).load(Uri.parse(imageUris.get(1))).into(imageBottomLeft);
            Glide.with(this).load(Uri.parse(imageUris.get(2))).into(imageBottomRight);
        } catch (Exception e) {
            Log.e("ThreeImageLayoutActivity", "Error loading images: " + e.getMessage());
        }
    }

    private void toggleLayout() {
        if (imageUris.size() < 3) {
            Log.e("ThreeImageLayoutActivity", "Cannot toggle layout: Image list is invalid.");
            return;
        }

        try {
            // Swap images for a different visual arrangement
            if (isGridLayout) {
                Collections.swap(imageUris, 0, 1); // Swap imageTop and imageBottomLeft
            } else {
                Collections.swap(imageUris, 1, 0); // Revert swap
            }

            // Reload images after swapping
            loadImages();
            isGridLayout = !isGridLayout; // Toggle state
        } catch (Exception e) {
            Log.e("ThreeImageLayoutActivity", "Error swapping images: " + e.getMessage());
        }
    }

    private void sendSelectedLayout() {
        if (imageUris.isEmpty()) {
            Log.e("ThreeImageLayoutActivity", "Cannot proceed: No images selected.");
            Toast.makeText(this, "No images available to proceed.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(ThreeImageLayoutActivity.this, FrameSelectionActivity.class);
        intent.putExtra("isGridLayout", isGridLayout);
        intent.putStringArrayListExtra("selectedImages", imageUris); // Send images as Strings
        startActivity(intent);
    }
}
