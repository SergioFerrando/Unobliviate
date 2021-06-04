 package com.example.pis_entrega1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.pis_entrega1.Note.Recording;
import com.example.pis_entrega1.R;

import java.io.File;
import com.example.pis_entrega1.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

 /**
  * Class hosting the audio recording layout
  */
 public class AudioNote extends AppCompatActivity implements View.OnClickListener {
    private MediaRecorder recorder;
    private boolean isRecording = false;
    Recording rec = new Recording();
    EditText titleView;

     /**
      * Method for setting all layout attributes and the Click Listeners of the buttons
      * @param savedInstanceState
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_note);
        findViewById(R.id.audioDirectButton).setOnClickListener(this);
        findViewById(R.id.AudioDeleteButton).setOnClickListener(this);
        titleView = this.findViewById(R.id.editTextAudioNote);
        String titleTemp = titleView.getText().toString();
        if (titleTemp != null) {
            this.rec.setName(titleTemp);
        } else {
            rec.setName("");
        }if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.RECORD_AUDIO
            },100);
        }
    }

     /**
      * Method to save the audio after passing by AudioRecorder class.
      * Transfer the info from AudioRecorded class to Main Activity class.
      * The Intent contains:
      * -Title of Audio Record
      * -Date of Audio Record
      * -Local Address of Audio Record
      * @param requestCode
      * @param resultCode
      * @param intent
      */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent();
                i.putExtra("title_audio_main", intent.getStringExtra("title_audio"));
                i.putExtra("date_audio_main", intent.getStringExtra("date_audio"));
                i.putExtra("Adress_main", intent.getStringExtra("Adress"));
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }

     /**
      * Method to go to AudioRecorded after recorded the audio note.
      * In the intent we will put:
      * -The Local Address of the AudioRecord
      * -The title of AudioRecord
      */
    public void goToAudioRecorded(){
        Intent n1 = new Intent(this, AudioRecorded.class);
        n1.putExtra("Adress", rec.getAddress());
        this.rec.setName(titleView.getText().toString());
        n1.putExtra("titleAudio", this.rec.getName());
        startActivityForResult(n1, 1);
    }

     /**
      * Method to control all the click listeners of the view
      * - If the delete button is touched then go tho Main Activity class
      * - If the Audio button is touched then record the audio, if the audio is
      * recorded go to AudioRecorded
      * @param v
      */
    @Override
    public void onClick(View v) {
        if (R.id.TextDeleteButton == v.getId()){
            stopRecording();
            Intent intent = new Intent();
            intent.putExtra("positionDelete", -1);
            setResult(RESULT_CANCELED, intent);
            finish();
        }if(R.id.audioDirectButton == v.getId()){
            if(isRecording){
                stopRecording();
                goToAudioRecorded();
            }else{
                startRecording();
            }
        }
    }

     /**
      * Method to stop Recording the voice
      */
    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;
    }

     /**
      * Method to start recording the voice by microphone and create a file to store the recording.
      */
    private void startRecording() {
        Log.d("startRecording", "startRecording");

        recorder = new MediaRecorder();
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss", Locale.GERMANY);
        String date = df.format(Calendar.getInstance().getTime());
        rec.setAddress(getExternalCacheDir().getAbsolutePath()+ File.separator +date+".mp3");
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
}