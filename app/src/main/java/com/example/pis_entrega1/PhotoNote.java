package com.example.pis_entrega1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class PhotoNote extends AppCompatActivity implements View.OnClickListener {
    private NotesContainer container = new NotesContainer();
    private Photo photo = new Photo();
    ImageView prueba;

    public PhotoNote(){}

    Photo p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setContainer((NotesContainer) getIntent().getParcelableExtra("MyClass"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_note);
        findViewById(R.id.photoDirectButton).setOnClickListener(this);
        findViewById(R.id.photoDeleteButton).setOnClickListener(this);
        prueba = findViewById(R.id.Foto);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
    }

    public PhotoNote(NotesContainer nc) {
        this.setContainer(nc);
    }

    public NotesContainer getContainer() {
        return container;
    }

    public void setContainer(NotesContainer container) {
        this.container = container;
    }

    public void goToMainIntent(){
        Intent n = new Intent(this, MainActivity.class);
        startActivity(n);
    }


    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MyClass", (Parcelable) container);
        startActivity(i);
        finish();
    }


    public void goToPhotoTaken(){
        p.setMiniatura(prueba);
        Intent n = new Intent(this, photo_taken.class);
        n.putExtra("Container", container);
        startActivity(n);
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
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,90, stream);
            byte[] image = stream.toByteArray();
            Intent n = new Intent(this, photo_taken.class);
            n.putExtra("photo", image);
            n.putExtra("Container", container);
            startActivity(n);
        }
    }
}