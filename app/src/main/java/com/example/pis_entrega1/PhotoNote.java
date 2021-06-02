package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoNote extends AppCompatActivity implements View.OnClickListener {

    private Photo photo;
    EditText title;
    String path;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    public PhotoNote(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_note);
        findViewById(R.id.photoDirectButton).setOnClickListener(this);
        findViewById(R.id.photoDeleteButton).setOnClickListener(this);
        photo = new Photo();
        title = findViewById(R.id.editTextTitlePhotoNote);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
    }

    public void goToMainIntent(){
        Intent intent = new Intent();
        intent.putExtra("positionDelete", -1);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.photoDeleteButton){
            goToMainIntent();
        }if(v.getId() == R.id.photoDirectButton){
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try{
            photoFile = createImageFile();
        }catch(IOException ex){
        }if(photoFile != null){
            Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        this.path = image.getAbsolutePath();
        return image;
    }


        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                photo.setName(title.getText().toString());
                photo.setAddress(this.path);

                Intent n = new Intent(this, PhotoTaken.class);
                n.putExtra("path", photo.getAddress());
                n.putExtra("titlePhoto", photo.getName());
                startActivityForResult(n, 1);
            }
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    String nameTemp = data.getStringExtra("title_photo");
                    String dateTemp = data.getStringExtra("date_photo");
                    String path = data.getStringExtra("path");
                    Intent i = new Intent();
                    i.putExtra("title_photo_main", nameTemp);
                    i.putExtra("date_photo_main", dateTemp);
                    i.putExtra("Adress_main", path);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
    }
}