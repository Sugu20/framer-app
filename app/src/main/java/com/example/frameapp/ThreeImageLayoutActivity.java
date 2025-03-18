package com.example.frameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ThreeImageLayoutActivity extends AppCompatActivity {

    private boolean isGridLayout = true; // Default layout is Grid
    private Button toggleButton, chooseFrameButton;
    private ImageView imageTop, imageBottomLeft, imageBottomRight;

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

        // Toggle Layout Button Click Listener
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout();
            }
        });

        // Choose Frame Button Click Listener
        chooseFrameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSelectedLayout();
            }
        });
    }

    private void toggleLayout() {
        if (isGridLayout) {
            // Swap images for a different visual arrangement
            ImageView temp = imageBottomLeft;
            imageBottomLeft.setImageDrawable(imageTop.getDrawable());
            imageTop.setImageDrawable(temp.getDrawable());
        } else {
            // Revert back to original layout
            ImageView temp = imageTop;
            imageTop.setImageDrawable(imageBottomLeft.getDrawable());
            imageBottomLeft.setImageDrawable(temp.getDrawable());
        }
        isGridLayout = !isGridLayout; // Toggle state
    }

    private void sendSelectedLayout() {
        Intent intent = new Intent(ThreeImageLayoutActivity.this, FrameSelectionActivity.class);
        intent.putExtra("isGridLayout", isGridLayout);

        // Send Image IDs
        intent.putExtra("image1", R.id.imageTop);
        intent.putExtra("image2", R.id.imageBottomLeft);
        intent.putExtra("image3", R.id.imageBottomRight);

        startActivity(intent);
    }
}
