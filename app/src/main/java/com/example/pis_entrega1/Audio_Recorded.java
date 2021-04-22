package com.example.pis_entrega1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import java.io.IOException;


public class Audio_Recorded extends AppCompatActivity implements View.OnClickListener {
    public Recording rec;
    public NotesContainer nc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.AudioDeleteButton).setOnClickListener(this);
        findViewById(R.id.AudioSaveButton).setOnClickListener(this);
        findViewById(R.id.AudioShareButton).setOnClickListener(this);
        findViewById(R.id.AudioRememberButton).setOnClickListener(this);
        findViewById(R.id.play_button).setOnClickListener(this);
        goFromAudioRecord();
    }

    public void goFromAudioRecord(){
        rec.setAddress(getIntent().getExtras().get("Adress").toString());
        nc = (NotesContainer) getIntent().getExtras().get("Container");
    }
    public void goToMainIntent(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void goToShareIntent(){
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = ContactsContract.CommonDataKinds.Note.getText().toString();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Text Note");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent,"Share using... "));*/
    }

    public void goToRememberIntent(){
        DatePickerFragment calendar = new DatePickerFragment();
        calendar.show(getSupportFragmentManager(),"DatePicker");
        TimePickerFragment hour = new TimePickerFragment();
        hour.show(getSupportFragmentManager(),"HOUR");
    }

    @Override
    public void onClick(View v) {
        if(R.id.AudioDeleteButton == v.getId()){
            goToMainIntent();
        }
        if(R.id.AudioSaveButton == v.getId()){
            this.rec.setName(this.findViewById(R.id.editTextTitleTextNote));
            nc.addAudioNote(this.rec.getName(), this.rec.getAddress());
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("MyClass", nc);
            startActivity(i);
        }
        if(R.id.AudioShareButton == v.getId()){
            goToShareIntent();
        }
        if(R.id.AudioRememberButton == v.getId()){
            goToRememberIntent();
        }
        if(R.id.play_button == v.getId()){
            startPlaying();
        }
    }

    public void startPlaying() {

        try {
            MediaPlayer player = new MediaPlayer();
            Log.d("startPlaying", rec.getAddress());
            player.setDataSource(rec.getAddress());
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.d("startPlaying", "prepare() failed");
        }
    }
}