package com.example.takeimagebeutask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private String currentPhotoPath;

    private TextView textView;
    private Button takeImage;
    private Button saveImage;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveImage = findViewById(R.id.buttonSaveImage);
        takeImage = findViewById(R.id.buttonTakeImage);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        takeImage.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });
        saveImage.setOnClickListener(v -> {
            saveImageToGallery();
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }

    private void saveImageToGallery(){
        imageView.setDrawingCacheEnabled(true);
        Bitmap b = imageView.getDrawingCache();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        MediaStore.Images.Media.insertImage(getContentResolver(), b,String.format(timeStamp,".jpg"), "take image BEU task");
        Toast.makeText(getApplicationContext(), "saved!", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);
            textView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);

        }
    }
}