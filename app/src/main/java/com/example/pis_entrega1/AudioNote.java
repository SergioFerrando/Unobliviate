package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AudioNote extends AppCompatActivity {

    private NotesContainer container;

    public AudioNote(NotesContainer nc) {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_note);
    }
}