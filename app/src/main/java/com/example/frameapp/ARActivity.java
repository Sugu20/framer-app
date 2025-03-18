package com.example.frameapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

import java.io.InputStream;

public class ARActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private Button btnBook, btnReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
        btnBook = findViewById(R.id.btn_book);
        btnReject = findViewById(R.id.btn_reject);

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            Uri imageUri = intent.getData();
            loadImageInAR(imageUri);
        } else {
            Toast.makeText(this, "No image URI received", Toast.LENGTH_LONG).show();
        }

        // Handle Book button click
        btnBook.setOnClickListener(v ->
                Toast.makeText(ARActivity.this, "Your frame has been booked!", Toast.LENGTH_LONG).show()
        );

        // Handle Reject button click
        btnReject.setOnClickListener(v -> {
            Intent dashboardIntent = new Intent(ARActivity.this, DashboardActivity.class);
            startActivity(dashboardIntent);
            finish(); // Close ARActivity
        });
    }

    private void loadImageInAR(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Placeholder material for now, as Bitmap-to-Texture isn't supported directly in Sceneform
            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.WHITE))
                    .thenAccept(material -> {
                        ModelRenderable planeRenderable = ShapeFactory.makeCube(
                                new Vector3(1.0f, 0.01f, 1.0f),
                                Vector3.zero(),
                                material
                        );

                        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                            Anchor anchor = hitResult.createAnchor();
                            AnchorNode anchorNode = new AnchorNode(anchor);
                            anchorNode.setRenderable(planeRenderable);
                            arFragment.getArSceneView().getScene().addChild(anchorNode);
                        });
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load image in AR", Toast.LENGTH_LONG).show();
        }
    }
}
