package com.example.pis_entrega1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean permissionToRecordAccepted = true;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.TextButton).setOnClickListener(this);
        findViewById(R.id.AudioButton).setOnClickListener(this);
        findViewById(R.id.CameraButton).setOnClickListener(this);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    public void goToTextNote(){
        Intent i = new Intent(this, TextNote.class);
        startActivity(i);
    }

    public void goTOAudioNote(){
        Intent i = new Intent(this, AudioNote.class);
        startActivity(i);
    }

    public void goToCameraNote(){
        Intent i = new Intent(this, PhotoNote.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(R.id.TextButton == v.getId()){
            goToTextNote();
        }
        if(R.id.AudioButton == v.getId()){
            goTOAudioNote();
        }
        if(R.id.CameraButton == v.getId()){
            goToCameraNote();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted ) finish();
    }
}