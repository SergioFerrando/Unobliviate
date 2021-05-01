package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class photo_taken extends AppCompatActivity implements View.OnClickListener{

    private NotesContainer container;
    Photo p;
    ImageView miniatura;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_taken);
        findViewById(R.id.PhotoDeleteButton).setOnClickListener(this);
        findViewById(R.id.PhotoRememberButton).setOnClickListener(this);
        findViewById(R.id.PhotoSaveButton).setOnClickListener(this);
        findViewById(R.id.PhotoShareButton).setOnClickListener(this);
        miniatura = findViewById(R.id.Foto);
        title = findViewById(R.id.editPhotoTitleNote);
        goFromPhotoNote();
    }

    public void goFromPhotoNote(){
        p.setMiniatura((Bitmap) getIntent().getExtras().get("img"));
        container = (NotesContainer) getIntent().getExtras().get("Container");
        miniatura.setImageBitmap(p.getMiniatura());
        p.setPhotoTitle(title.getText().toString());
    }

    public void goToMainIntentNoSave(){
        Intent n = new Intent(this,MainActivity.class);
        startActivity(n);
    }

    public void goToMainIntentSave(){
        try {
            container.addPhotoNote(this.p.getName(),p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.PhotoDeleteButton){
            goToMainIntentNoSave();
        }if(v.getId() == R.id.PhotoSaveButton){
            goToMainIntentSave();
        }

    }
}