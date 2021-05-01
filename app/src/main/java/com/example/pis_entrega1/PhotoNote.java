package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;

public class PhotoNote extends AppCompatActivity implements View.OnClickListener {
    private NotesContainer container = new NotesContainer();
    private Photo photo = new Photo();

    public PhotoNote(){}

    Photo p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setContainer((NotesContainer) getIntent().getParcelableExtra("MyClass"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_note);
        findViewById(R.id.photoDirectButton).setOnClickListener(this);
        findViewById(R.id.photoDeleteButton).setOnClickListener(this);
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            p.setMiniatura(imageBitmap);
        }
    }

    public void goToPhotoTaken(){
        Intent n = new Intent(this, photo_taken.class);
        n.putExtra("img", p.getMiniatura());
        n.putExtra("Container", container);
        startActivity(n);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.photoDeleteButton){
            goToMainIntent();
        }if(v.getId() == R.id.photoDirectButton){
            dispatchTakePictureIntent();
        }
    }
}