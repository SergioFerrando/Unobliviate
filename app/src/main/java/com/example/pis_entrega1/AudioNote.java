package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AudioNote extends AppCompatActivity implements View.OnClickListener {
    NotesContainer nc = new NotesContainer();
    private MediaRecorder recorder;
    private boolean isRecording = false;
    Recording rec = new Recording();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_note);
        findViewById(R.id.audioDirectButton).setOnClickListener(this);
        findViewById(R.id.AudioDeleteButton).setOnClickListener(this);
        String titleTemp = this.findViewById(R.id.editTextAudioNote).toString();
        if (titleTemp != null){
            this.rec.setName(titleTemp);
        }else{
            rec.setName("");
        }
        //this.getFromMainActivity();
        //this.getFromMyAdapter();
    }

    public void goToMainIntent(){
        Intent n = new Intent(this, MainActivity.class);
        startActivity(n);
    }

    public void goToAudioRecorded(){
        Intent n1 = new Intent(this, AudioRecorded.class);
        n1.putExtra("Adress", rec.getAddress());
        n1.putExtra("Container", nc);
        startActivity(n1);
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextDeleteButton == v.getId()){
            if(isRecording){
                stopRecording();
                goToMainIntent();
            }else {
                goToMainIntent();
            }
        }if(R.id.audioDirectButton == v.getId()){
            if(isRecording){
                stopRecording();
                goToAudioRecorded();
            }else{
                startRecording();
            }
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;
    }

    private void startRecording() {
        Log.d("startRecording", "startRecording");

        recorder = new MediaRecorder();
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss", Locale.GERMANY);
        String date = df.format(Calendar.getInstance().getTime());
        rec.setAddress(getExternalCacheDir().getAbsolutePath()+ File.separator +date+".3gp");
        Log.d("startRecording", rec.getAddress());

        recorder.setOutputFile(rec.getAddress());
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.d("startRecording", "prepare() failed");
        }
        recorder.start();
        isRecording = true;
    }

    public void getFromMainActivity(){
        NotesContainer notesContainer = getIntent().getExtras().getParcelable("MyClass");
        if (notesContainer != null){
            this.nc = notesContainer;
        }
    }
}