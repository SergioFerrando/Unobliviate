package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class AudioRecorded extends AppCompatActivity implements View.OnClickListener{
    public Recording rec = new Recording();
    public NotesContainer nc;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorded);
        findViewById(R.id.AudioDeleteButton).setOnClickListener(this);
        findViewById(R.id.AudioSaveButton).setOnClickListener(this);
        findViewById(R.id.AudioShareButton).setOnClickListener(this);
        findViewById(R.id.AudioRememberButton).setOnClickListener(this);
        findViewById(R.id.play_button).setOnClickListener(this);
        name = this.findViewById(R.id.editTextAudioRecordedNote);
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
        String sharePath = rec.getAddress();
        Uri uri = Uri.parse(sharePath);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Sound File"));
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
            this.rec.setName(name.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("title_audio", this.rec.getName());
            intent.putExtra("date_audio", System.currentTimeMillis());
            intent.putExtra("Adress", this.rec.getAddress());
            setResult(RESULT_OK, intent);
            finish();
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