package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.TextButton).setOnClickListener(this);
        findViewById(R.id.AudioButton).setOnClickListener(this);
        findViewById(R.id.CameraButton).setOnClickListener(this);
    }
    public void goToTextNote(){
        Intent i = new Intent(this, TextNote.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(R.id.TextButton == v.getId()){
            goToTextNote();
        }
        if(R.id.AudioButton == v.getId()){

        }
        if(R.id.CameraButton == v.getId()){

        }
    }
}