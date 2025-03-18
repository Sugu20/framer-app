package com.example.frameapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class FiveImageLayoutActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView;
    private ImageView image1, image2, image3, image4, image5;
    private Button toggleButton, nextButton;
    private boolean isGridLayout1 = true; // Tracks layout state
    private Uri[] imageUris = new Uri[5]; // Stores selected image URIs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_image_layout);

        // Initialize ImageViews
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);

        toggleButton = findViewById(R.id.toggleButton);
        nextButton = findViewById(R.id.newButton);

        // Set click listeners for selecting images
        setImageClickListener(image1, 0);
        setImageClickListener(image2, 1);
        setImageClickListener(image3, 2);
        setImageClickListener(image4, 3);
        setImageClickListener(image5, 4);

        // Toggle layout on button click
        toggleButton.setOnClickListener(v -> toggleLayout());

        // Navigate to Frame Selection Activity
        nextButton.setOnClickListener(v -> openFrameSelectionActivity());
    }

    // Set click listener to open gallery for each image
    private void setImageClickListener(ImageView imageView, int index) {
        imageView.setOnClickListener(v -> {
            selectedImageView = imageView;
            selectedImageView.setTag(index); // Store index to track selected image
            openGallery();
        });
    }

    // Open gallery to pick an image
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle selected image from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null && selectedImageView != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageView.setImageBitmap(bitmap);

                    // Store URI for passing to next activity
                    int index = (int) selectedImageView.getTag();
                    imageUris[index] = imageUri;

                    Log.d("FiveImageLayoutActivity", "Image selected at index: " + index + " URI: " + imageUri);
                } catch (IOException e) {
                    Log.e("FiveImageLayoutActivity", "Error loading image: " + e.getMessage());
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Toggle between different layouts
    private void toggleLayout() {
        if (isGridLayout1) {
            toggleButton.setText("Switch to Layout 1");
            adjustImageSize(image3, 150, getResources().getDisplayMetrics().widthPixels / 2);
        } else {
            toggleButton.setText("Switch to Layout 2");
            adjustImageSize(image3, 200, getResources().getDisplayMetrics().widthPixels);
        }
        isGridLayout1 = !isGridLayout1; // Toggle state
    }

    // Dynamically adjust image size
    private void adjustImageSize(ImageView imageView, int height, int width) {
        imageView.getLayoutParams().height = height;
        imageView.getLayoutParams().width = width;
        imageView.requestLayout();
    }

    // Pass image URIs and layout state to FrameSelectionActivity
    private void openFrameSelectionActivity() {
        Intent intent = new Intent(this, FrameSelectionActivity.class);

        boolean hasImages = false;
        for (int i = 0; i < imageUris.length; i++) {
            if (imageUris[i] != null) {
                intent.putExtra("image" + (i + 1), imageUris[i].toString());
                hasImages = true;
            }
        }

        if (!hasImages) {
            Toast.makeText(this, "Please select at least one image", Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra("isGridLayout1", isGridLayout1);
        startActivity(intent);
    }
}
