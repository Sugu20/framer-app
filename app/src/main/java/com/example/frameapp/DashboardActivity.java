package com.example.frameapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.frameapp.databinding.ActivityDashboardBinding;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private ImageAdapter imageAdapter;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private static final int MAX_IMAGES = 5;
    private String phoneNumber;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetMultipleContents(),
            uris -> {
                if (uris.size() > MAX_IMAGES) {
                    Toast.makeText(this, "You can only select up to 5 images", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUris.clear();
                imageUris.addAll(uris);
                imageAdapter.notifyDataSetChanged();
                updateNextButtonState();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("phone_number", null);

        setupRecyclerView();
        setClickListeners();
    }

    private void setupRecyclerView() {
        binding.imageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imageAdapter = new ImageAdapter(this, imageUris);
        binding.imageRecyclerView.setAdapter(imageAdapter);
    }

    private void setClickListeners() {
        binding.btnUpload.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        binding.btnNext.setOnClickListener(v -> {
            if (imageUris.size() < 2) {
                Toast.makeText(this, "Please select at least 2 images", Toast.LENGTH_SHORT).show();
                return;
            }
            navigateToNextActivity();
        });
    }

    private void updateNextButtonState() {
        binding.btnNext.setEnabled(imageUris.size() >= 2);
    }

    private void navigateToNextActivity() {
        Intent intent = null;
        switch (imageUris.size()) {
            case 2:
                intent = new Intent(this, TwoImageLayoutActivity.class);
                break;
            case 3:
                intent = new Intent(this, ThreeImageLayoutActivity.class);
                break;
            case 4:
                intent = new Intent(this, FourImageLayoutActivity.class);
                break;
            case 5:
                intent = new Intent(this, FiveImageLayoutActivity.class);
                break;
            default:
                Toast.makeText(DashboardActivity.this, "Please select at least 2 images to continue", Toast.LENGTH_SHORT).show();
                return;
        }

        // Convert Uri list to String list
        ArrayList<String> stringUris = new ArrayList<>();
        for (Uri uri : imageUris) {
            stringUris.add(uri.toString());
        }

        intent.putStringArrayListExtra("selectedImages", stringUris); // Correct key and format
        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }
}
