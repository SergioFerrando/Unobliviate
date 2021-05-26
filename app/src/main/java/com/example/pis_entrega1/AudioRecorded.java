package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AudioRecorded extends AppCompatActivity implements View.OnClickListener{
    public Recording rec = new Recording();
    EditText name;
    int position = -1;
    ImageButton re;

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
        re = this.findViewById(R.id.AudioRememberButton);
        goFromAudioRecord();
    }

    public void goFromAudioRecord(){
        if (getIntent().getStringExtra("newTitleAudio") != null){
            this.rec.setName(getIntent().getStringExtra("newTitleAudio"));
            name.setText(this.rec.getName());
            this.rec.setAddress(getIntent().getStringExtra("Adress"));
            this.rec.setID(getIntent().getStringExtra("id"));
            this.rec.setUrl(getIntent().getStringExtra("url"));
            this.position = getIntent().getIntExtra("positionAudio", 0);
        } else{
            rec.setAddress(getIntent().getExtras().get("Adress").toString());
            rec.setName(getIntent().getStringExtra("titleAudio"));
            name.setText(rec.getName());
        }
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
        Calendar actual = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        int año = actual.get(Calendar.YEAR);
        int mes = actual.get(Calendar.MONTH);
        int dia = actual.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog fecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);

                int hour = actual.get(Calendar.HOUR_OF_DAY);
                int minutos = actual.get(Calendar.MINUTE);

                TimePickerDialog hora = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        re.setImageResource(R.drawable.ic_baseline_access_alarms_24);
                    }
                },hour,minutos, true);
                hora.show();
            }
        },año,mes,dia);
        fecha.show();
    }

    @Override
    public void onClick(View v) {
        if(R.id.AudioDeleteButton == v.getId()){
            Intent intent = new Intent();
            intent.putExtra("positionDelete", rec.getID());
            setResult(RESULT_CANCELED, intent);
            finish();
        }
        if(R.id.AudioSaveButton == v.getId()){
            if(rec.getID() == null) {
                this.rec.setName(name.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("title_audio", this.rec.getName());
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
                String date = df.format(Calendar.getInstance().getTime());
                intent.putExtra("date_audio", date);
                intent.putExtra("Adress", this.rec.getAddress());
                setResult(RESULT_OK, intent);
                finish();
            } else{
                this.rec.setName(name.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("title_audio", this.rec.getName());
                intent.putExtra("Adress", this.rec.getAddress());
                intent.putExtra("id", this.rec.getID());
                intent.putExtra("url", this.rec.getUrl());
                intent.putExtra("positionAudio", this.position);
                setResult(2, intent);
                finish();
            }
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