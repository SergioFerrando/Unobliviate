package com.example.pis_entrega1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.media.MediaRecorder;
import android.util.Log;

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
