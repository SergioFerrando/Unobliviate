package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Class used when the audio has already recorded
 */
public class AudioRecorded extends AppCompatActivity implements View.OnClickListener{
    public Recording rec = new Recording();
    EditText name;
    int position = -1;

    /**
     * Method for setting all layout attributes and the Click Listeners of the buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorded);
        findViewById(R.id.AudioDeleteButton).setOnClickListener(this);
        findViewById(R.id.AudioSaveButton).setOnClickListener(this);
        findViewById(R.id.AudioShareButton).setOnClickListener(this);
        findViewById(R.id.play_button).setOnClickListener(this);
        name = this.findViewById(R.id.editTextAudioRecordedNote);
        goFromAudioRecord();
    }

    /**
     * Method to get the information of the audio recorded and show the info of it on layout.
     */
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

    /**
     * Method to share the actual audio note by the android apps (extension of the file to share .mp3)
     */
    public void goToShareIntent(){
        File file = new File(this.rec.getAddress());
        Uri path = FileProvider.getUriForFile(getApplicationContext(), "com.example.android.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, path);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("audio/mp3");

        Intent chooser = Intent.createChooser(shareIntent, "Share...");

        List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            this.grantUriPermission(packageName, path, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        startActivity(chooser);
    }

    /**
     * Method to control all the click listeners of the view
     * - If delete button is clicked go to VentanaFlotante Class
     * - If Save Button is clicked, go to Audio Note passing the information of the audio
     * - If Share button is clicked go to method goToShareIntent()
     * - If play button is clicked go to startPlaying()
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(R.id.AudioDeleteButton == v.getId()){
            Intent i = new Intent(this, VentanaFlotante.class);
            startActivityForResult(i, 1);
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
        if(R.id.play_button == v.getId()){
            startPlaying();
        }
    }

    /**
     * Method to reproduce the actual audio note
     */
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

    /**
     * Method to go to confirmation discard changes
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (intent.getBooleanExtra("result_verification", false)) {
                    Intent i = new Intent();
                    i.putExtra("positionDelete", rec.getID());
                    setResult(RESULT_CANCELED, i);
                    finish();
                }
            }
        }
    }

}