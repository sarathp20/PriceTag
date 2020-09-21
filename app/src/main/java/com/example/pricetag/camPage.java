package com.example.pricetag;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pricetag.classifier.ImageClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class camPage extends AppCompatActivity {

    Button retake;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQEUST_CODE = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        initializeUIElements();
        retake=findViewById(R.id.startCamera2);
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeUIElements();
            }
        });

    }
    private void initializeUIElements() {

        try {
            if (hasPermission()) {
                openCamera();
            } else {
                requestPermission();
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        ImageView imageView;

        imageView = findViewById(R.id.iv_capture);
        TextView text = findViewById(R.id.detectedImage);
        if (requestCode == CAMERA_REQEUST_CODE) {

            Bitmap photo = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");

            imageView.setImageBitmap(photo);


            ImageClassifier imageClassifier = null;
            try {
                imageClassifier = new ImageClassifier(this);
            } catch (IOException e) {
               System.out.println("error in object creation of classifier  "+e);
            }
            List<ImageClassifier.Recognition> predicitons = imageClassifier.recognizeImage(
                    photo, 0);
            final List<String> predicitonsList = new ArrayList<>();
            for (ImageClassifier.Recognition recog : predicitons) {
                predicitonsList.add(recog.getName());
            }
            text.setText(predicitonsList.get(0));



        }
            super.onActivityResult(requestCode, resultCode, data);
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (hasAllPermissions(grantResults)) {
                openCamera();
            } else {
                requestPermission();
            }
        }
    }
    private boolean hasAllPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);

        startActivityForResult(cameraIntent, CAMERA_REQEUST_CODE);
    }
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }






}
