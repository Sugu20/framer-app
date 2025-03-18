package com.example.frameapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class TwoImageLayoutActivity extends AppCompatActivity {
    private ImageView imageTop, imageBottom, imageLeft, imageRight;
    private View topBottomBox, leftRightBox;
    private Button toggleButton, chooseFrameButton;
    private boolean isTopBottomLayout = true; // Default layout
    private ArrayList<String> imageUris; // Store selected images

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
        imageUris = intent.getStringArrayListExtra("selectedImages");

        if (imageUris != null && imageUris.size() >= 2) {
            Log.d("TwoImageLayoutActivity", "Received Images: " + imageUris);

            // Load images into both layouts
            loadImages(imageUris.get(0), imageUris.get(1));
        } else {
            Log.e("TwoImageLayoutActivity", "No images received or insufficient images.");
        }

        // Set up click listeners
        toggleButton.setOnClickListener(v -> toggleLayout());
        chooseFrameButton.setOnClickListener(v -> openFrameSelection());
    }

    private void loadImages(String uri1, String uri2) {
        Glide.with(this).load(Uri.parse(uri1)).into(imageTop);
        Glide.with(this).load(Uri.parse(uri2)).into(imageBottom);
        Glide.with(this).load(Uri.parse(uri1)).into(imageLeft);
        Glide.with(this).load(Uri.parse(uri2)).into(imageRight);
    }

    private void toggleLayout() {
        boolean isNowTopBottom = topBottomBox.getVisibility() == View.VISIBLE;
        topBottomBox.setVisibility(isNowTopBottom ? View.GONE : View.VISIBLE);
        leftRightBox.setVisibility(isNowTopBottom ? View.VISIBLE : View.GONE);
    }

    private void openFrameSelection() {
        if (imageUris != null && !imageUris.isEmpty()) {
            Intent intent = new Intent(TwoImageLayoutActivity.this, FrameSelectionActivity.class);
            intent.putStringArrayListExtra("selectedImages", imageUris);
            startActivity(intent);
        } else {
            Log.e("TwoImageLayoutActivity", "Cannot proceed: No images selected.");
        }
    }
}
