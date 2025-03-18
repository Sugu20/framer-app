package com.example.frameapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Collections;

public class FourImageLayoutActivity extends AppCompatActivity {
    private ImageView image1, image2, image3, image4;
    private Button toggleButton, chooseFrameButton;
    private ArrayList<String> imageUris;

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
        imageUris = intent.getStringArrayListExtra("selectedImages");

        // Ensure there are at least 4 images, otherwise initialize an empty list
        if (imageUris == null || imageUris.size() < 4) {
            imageUris = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                imageUris.add(""); // Placeholder for missing images
            }
        }

        // Load Images into ImageViews
        loadImages();

        // Toggle Layout on Button Click
        toggleButton.setOnClickListener(v -> switchImages());

        // Navigate to Frame Selection Activity & Pass Image Data
        chooseFrameButton.setOnClickListener(v -> {
            Intent frameIntent = new Intent(FourImageLayoutActivity.this, FrameSelectionActivity.class);
            frameIntent.putStringArrayListExtra("selectedImages", imageUris);
            startActivity(frameIntent);
        });
    }

    private void loadImages() {
        Glide.with(this).load(getValidUri(imageUris.get(0))).into(image1);
        Glide.with(this).load(getValidUri(imageUris.get(1))).into(image2);
        Glide.with(this).load(getValidUri(imageUris.get(2))).into(image3);
        Glide.with(this).load(getValidUri(imageUris.get(3))).into(image4);
    }

    private void switchImages() {
        if (imageUris.size() >= 4) {
            Collections.shuffle(imageUris); // Shuffle images randomly
            loadImages(); // Reload images in new order
        }
    }

    // Ensure URI is valid before loading
    private Uri getValidUri(String uri) {
        return uri.isEmpty() ? Uri.parse("android.resource://com.example.frameapp/drawable/placeholder") : Uri.parse(uri);
    }
}
