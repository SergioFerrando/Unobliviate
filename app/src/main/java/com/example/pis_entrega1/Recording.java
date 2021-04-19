package com.example.pis_entrega1;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Locale;


public class Recording extends Notes {
    private MediaRecorder recorder;
    private boolean isRecording = false;
    String fileName;


    public Recording(Long date, String name, MediaRecorder audio)
    {
        super(date, name);
        this.recorder = audio;
    }

    public void startRecording() {
        Log.d("startRecording", "startRecording");

        recorder = new MediaRecorder();
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss", Locale.GERMANY);
        String date = df.format(Calendar.getInstance().getTime());
        //this.setDate(date);
        fileName = this.getName() + ".3gp";
        Log.d("startRecording", fileName);

        recorder.setOutputFile(fileName);
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

    public void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;
    }


    public MediaRecorder getAudio(){
        return this.recorder;
    }
    public void setAudio(MediaRecorder audio){
        this.recorder = audio;
    }
    public String getFileName(){
        return fileName;
    }
    public boolean getIsReording(){
        return isRecording;
    }
}
