package com.example.frameapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FinalPreview extends AppCompatActivity {

    private ImageView finalImageView, finalFrameView;
    private Button viewInArButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_preview);

        finalImageView = findViewById(R.id.finalImageView);  // Image inside the frame
        finalFrameView = findViewById(R.id.finalFrameView);  // Frame overlay
        viewInArButton = findViewById(R.id.viewInArButton);

        // Get selected frame and image from intent
        Intent intent = getIntent();
        if (intent != null) {
            int frameResId = intent.getIntExtra("selectedFrameResId", 0);
            String selectedImageUri = intent.getStringExtra("selectedImage");

            // Set the selected frame on the frame ImageView
            if (frameResId != 0) {
                finalFrameView.setImageResource(frameResId);
            }

            // Set the selected image inside the frame
            if (selectedImageUri != null) {
                Uri imageUri = Uri.parse(selectedImageUri);
                finalImageView.setImageURI(imageUri);
            }
        }

        // View in AR button click
        viewInArButton.setOnClickListener(v -> {
            Intent arIntent = new Intent(FinalPreview.this, ARActivity.class);
            arIntent.putExtra("selectedImage", intent.getStringExtra("selectedImage"));
            arIntent.putExtra("selectedFrameResId", intent.getIntExtra("selectedFrameResId", 0));
            startActivity(arIntent);
        });
    }
}
