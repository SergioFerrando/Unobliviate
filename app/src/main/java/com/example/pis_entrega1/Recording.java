package com.example.pis_entrega1;

import java.util.Date;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
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


public class Recording extends Notes {
    private MediaRecorder audio;

    public Recording(Long date, String name, MediaRecorder audio)
    {
        super(date, name);
        this.audio = audio;

    }

    public MediaRecorder getAudio(){
        return this.audio;
    }
    public void setAudio(MediaRecorder audio){
        this.audio = audio;
    }
}
