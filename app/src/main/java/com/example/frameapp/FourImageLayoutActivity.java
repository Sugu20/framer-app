package com.example.frameapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

public class FourImageLayoutActivity extends AppCompatActivity {
    private ImageView image1, image2, image3, image4;
    private Button toggleButton, chooseFrameButton;
    private ArrayList<String> imageUris = new ArrayList<>();
    private boolean isOriginalLayout = true; // Track layout state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_image_layout);

        // Initialize Views
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        toggleButton = findViewById(R.id.toggleButton);
        chooseFrameButton = findViewById(R.id.chooseFrameButton);

        // Retrieve Images from Intent
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> receivedImages = intent.getStringArrayListExtra("selectedImages");
            if (receivedImages != null && receivedImages.size() >= 4) {
                imageUris.addAll(receivedImages);
            }
        }

        // Ensure there are exactly 4 images, adding placeholders if necessary
        while (imageUris.size() < 4) {
            imageUris.add(""); // Placeholder for missing images
        }

        // Debugging Logs
        Log.d("FourImageLayoutActivity", "Received Images Count: " + imageUris.size());
        for (int i = 0; i < imageUris.size(); i++) {
            Log.d("FourImageLayoutActivity", "Image " + i + ": " + imageUris.get(i));
        }

        loadImages(); // Load images into views

        // Toggle Layout on Button Click
        toggleButton.setOnClickListener(v -> switchImages());

        // Navigate to Frame Selection Activity & Pass Image Data
        chooseFrameButton.setOnClickListener(v -> sendSelectedLayout());
    }

    private void loadImages() {
        try {
            Glide.with(this).load(getValidUri(imageUris.get(0))).into(image1);
            Glide.with(this).load(getValidUri(imageUris.get(1))).into(image2);
            Glide.with(this).load(getValidUri(imageUris.get(2))).into(image3);
            Glide.with(this).load(getValidUri(imageUris.get(3))).into(image4);
        } catch (Exception e) {
            Log.e("FourImageLayoutActivity", "Error loading images: " + e.getMessage());
        }
    }

    private void switchImages() {
        if (imageUris.size() < 4) {
            Log.e("FourImageLayoutActivity", "Not enough images to switch.");
            return;
        }

        try {
            if (isOriginalLayout) {
                // Swap image1 ↔ image3 and image2 ↔ image4
                Collections.swap(imageUris, 0, 2);
                Collections.swap(imageUris, 1, 3);
            } else {
                // Revert to original order
                Collections.swap(imageUris, 0, 2);
                Collections.swap(imageUris, 1, 3);
            }

            loadImages(); // Reload images after swapping
            isOriginalLayout = !isOriginalLayout; // Toggle state
        } catch (Exception e) {
            Log.e("FourImageLayoutActivity", "Error swapping images: " + e.getMessage());
        }
    }

    private void sendSelectedLayout() {
        if (imageUris.isEmpty()) {
            Log.e("FourImageLayoutActivity", "No images to proceed.");
            Toast.makeText(this, "No images available to proceed.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(FourImageLayoutActivity.this, FrameSelectionActivity.class);
        intent.putStringArrayListExtra("selectedImages", imageUris);
        startActivity(intent);
    }

    // Ensure URI is valid before loading
    private Uri getValidUri(String uri) {
        return uri.isEmpty() ? Uri.parse("android.resource://com.example.frameapp/drawable/placeholder") : Uri.parse(uri);
    }
}
