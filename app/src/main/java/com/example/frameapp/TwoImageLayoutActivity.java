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

public class TwoImageLayoutActivity extends AppCompatActivity {
    private ImageView imageTop, imageBottom, imageLeft, imageRight;
    private View topBottomBox, leftRightBox;
    private Button toggleButton, chooseFrameButton;
    private boolean isTopBottomLayout = true; // Default layout
    private ArrayList<String> imageUris = new ArrayList<>(); // Ensure initialization

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_image_layout);

        // Initialize views
        imageTop = findViewById(R.id.imageTop);
        imageBottom = findViewById(R.id.imageBottom);
        imageLeft = findViewById(R.id.imageLeft);
        imageRight = findViewById(R.id.imageRight);
        toggleButton = findViewById(R.id.toggleButton);
        chooseFrameButton = findViewById(R.id.chooseFrameButton);
        topBottomBox = findViewById(R.id.topBottomBox);
        leftRightBox = findViewById(R.id.leftRightBox);

        // Retrieve images from Intent
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> receivedImages = intent.getStringArrayListExtra("selectedImages");
            if (receivedImages != null && receivedImages.size() >= 2) {
                imageUris.addAll(receivedImages); // Copy received data
            }
        }

        // Debugging Logs
        Log.d("TwoImageLayoutActivity", "Received Images Count: " + imageUris.size());
        if (!imageUris.isEmpty()) {
            for (int i = 0; i < imageUris.size(); i++) {
                Log.d("TwoImageLayoutActivity", "Image " + i + ": " + imageUris.get(i));
            }
        }

        // Check if images are valid
        if (imageUris.size() < 2) {
            Log.e("TwoImageLayoutActivity", "No images received or insufficient images.");
            Toast.makeText(this, "Please select at least two images.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if images are not valid
            return;
        }

        loadImages();

        // Set up click listeners
        toggleButton.setOnClickListener(v -> toggleLayout());
        chooseFrameButton.setOnClickListener(v -> openFrameSelection());
    }

    private void loadImages() {
        if (imageUris.size() < 2) {
            Log.e("TwoImageLayoutActivity", "Error: Image list is invalid.");
            return;
        }

        try {
            if (isTopBottomLayout) {
                Glide.with(this).load(Uri.parse(imageUris.get(0))).into(imageTop);
                Glide.with(this).load(Uri.parse(imageUris.get(1))).into(imageBottom);
            } else {
                Glide.with(this).load(Uri.parse(imageUris.get(0))).into(imageLeft);
                Glide.with(this).load(Uri.parse(imageUris.get(1))).into(imageRight);
            }
        } catch (Exception e) {
            Log.e("TwoImageLayoutActivity", "Error loading images: " + e.getMessage());
        }
    }

    private void toggleLayout() {
        if (imageUris.size() < 2) {
            Log.e("TwoImageLayoutActivity", "Cannot toggle layout: Image list is invalid.");
            return;
        }

        try {
            // Swap images in the list
            Collections.swap(imageUris, 0, 1);

            // Toggle layout visibility
            isTopBottomLayout = !isTopBottomLayout;
            topBottomBox.setVisibility(isTopBottomLayout ? View.VISIBLE : View.GONE);
            leftRightBox.setVisibility(isTopBottomLayout ? View.GONE : View.VISIBLE);

            // Reload images after swapping
            loadImages();
        } catch (Exception e) {
            Log.e("TwoImageLayoutActivity", "Error swapping images: " + e.getMessage());
        }
    }

    private void openFrameSelection() {
        if (imageUris.isEmpty()) {
            Log.e("TwoImageLayoutActivity", "Cannot proceed: No images selected.");
            Toast.makeText(this, "No images available to proceed.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(TwoImageLayoutActivity.this, FrameSelectionActivity.class);
        intent.putStringArrayListExtra("selectedImages", imageUris);
        startActivity(intent);
    }
}
