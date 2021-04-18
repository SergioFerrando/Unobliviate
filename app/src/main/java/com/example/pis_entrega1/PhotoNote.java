package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

public class PhotoNote extends AppCompatActivity {

    private NotesContainer container;

    public PhotoNote(NotesContainer nc) {
        this.setContainer(nc);
    }

    public NotesContainer getContainer() {
        return container;
    }

    public void setContainer(NotesContainer container) {
        this.container = container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setContainer((NotesContainer) getIntent().getParcelableExtra("MyClass"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_note);
    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MyClass", (Parcelable) container);
        startActivity(i);
        finish();
    }
}