package com.example.frameapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class FrameSelectionActivity extends AppCompatActivity {

    private ImageView framePreview;
    private Button applyBlackFrame, applyWoodFrame, applyGoldFrame, applyArtisticFrame, confirmButton;

    private int selectedFrameResId; // Stores the selected frame ID
    private String layoutType;
    private ArrayList<String> imageUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_selection);

        framePreview = findViewById(R.id.framePreview);
        applyBlackFrame = findViewById(R.id.blackFrame);
        applyWoodFrame = findViewById(R.id.woodFrame);
        applyGoldFrame = findViewById(R.id.goldFrame);
        applyArtisticFrame = findViewById(R.id.artisticFrame);
        confirmButton = findViewById(R.id.confirmButton);

        // Get data from previous activity (layout type and selected images)
        Intent intent = getIntent();
        layoutType = intent.getStringExtra("layoutType");
        imageUris = intent.getStringArrayListExtra("imageUris");

        // Set click listeners to change frames
        applyBlackFrame.setOnClickListener(v -> setFrame(R.drawable.black_frame));
        applyWoodFrame.setOnClickListener(v -> setFrame(R.drawable.wood_frame));
        applyGoldFrame.setOnClickListener(v -> setFrame(R.drawable.gold_frame));
        applyArtisticFrame.setOnClickListener(v -> setFrame(R.drawable.artistic_frame));

        // Confirm Selection and Pass Data to FinalPreviewActivity
        confirmButton.setOnClickListener(v -> {
            Intent previewIntent = new Intent(FrameSelectionActivity.this, FinalPreview.class);
            previewIntent.putExtra("selectedFrameResId", selectedFrameResId);
            previewIntent.putExtra("layoutType", layoutType);
            previewIntent.putStringArrayListExtra("imageUris", imageUris);
            startActivity(previewIntent);
        });
    }

    private void setFrame(int drawableId) {
        selectedFrameResId = drawableId;
        Drawable frameDrawable = ContextCompat.getDrawable(this, drawableId);
        framePreview.setImageDrawable(frameDrawable); // Use setImageDrawable for better compatibility
    }
}
