package com.example.pis_entrega1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoNote extends AppCompatActivity implements View.OnClickListener {
    private Photo photo = new Photo();
    ImageView prueba;
    EditText title;
    String path;

    public PhotoNote(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_note);
        findViewById(R.id.photoDirectButton).setOnClickListener(this);
        findViewById(R.id.photoDeleteButton).setOnClickListener(this);
        prueba = findViewById(R.id.Foto);
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
            sacarFoto();
        }
    }

    public void sacarFoto(){
        Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                path = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent,100);
            }
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                title.getText().toString(),  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,90, stream);
            byte[] image = stream.toByteArray();

            photo.setName(title.getText().toString());
            photo.setAddress(this.path);

            Intent n = new Intent(this, PhotoTaken.class);
            n.putExtra("photo", image);
            n.putExtra("path", photo.getAddress());
            n.putExtra("titlePhoto", photo.getName());
            startActivityForResult(n, 1);
        } if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                String nameTemp = data.getStringExtra("title_photo");
                long dateTemp = data.getLongExtra("date_photo", 0);
                byte[] byteImage = data.getByteArrayExtra("photo");
                Intent i = new Intent();
                i.putExtra("title_photo_main", nameTemp);
                i.putExtra("date_photo_main", dateTemp);
                i.putExtra("byteImage_main", byteImage);
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }
}