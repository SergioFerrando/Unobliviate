package com.example.pis_entrega1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity_FALSO extends AppCompatActivity implements CustomAdapter.playerInterface {

    private final String TAG = "MainActivity";


    private Context parentContext;
    private AppCompatActivity mActivity;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private MediaRecorder recorder;
    private boolean isRecording = false;
    String fileName;


    private MainActivityViewModel viewModel;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentContext = this.getBaseContext();
        mActivity = this;
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setLiveDataObservers();


        // Floating button functionality
        ExtendedFloatingActionButton extendedFab = findViewById(R.id.extended_fab);
        extendedFab.setOnClickListener((v) -> {
            // Change Extended FAB aspect and handle recording
            if (isRecording) {
                extendedFab.extend();
                extendedFab.setIcon(
                        ContextCompat.getDrawable(
                                parentContext, android.R.drawable.ic_input_add));
                stopRecording();
                showPopup(mRecyclerView);

            } else {
                extendedFab.shrink();
                extendedFab.setIcon(
                        ContextCompat.getDrawable(
                                parentContext, android.R.drawable.ic_btn_speak_now));
                startRecording();
            }
        });

    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        final Observer<ArrayList<AudioCard>> observer = new Observer<ArrayList<AudioCard>>() {
            @Override
            public void onChanged(ArrayList<AudioCard> ac) {
                CustomAdapter newAdapter = new CustomAdapter(parentContext, ac, (CustomAdapter.playerInterface) mActivity);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();

            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getAudioCards().observe(this, observer);
        viewModel.getToast().observe(this, observerToast);

    }

    private void startRecording() {
        Log.d("startRecording", "startRecording");

        recorder = new MediaRecorder();
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss", Locale.GERMANY);
        String date = df.format(Calendar.getInstance().getTime());
        fileName =  getExternalCacheDir().getAbsolutePath()+ File.separator +date+".3gp";
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

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;

    }

    public void startPlaying(int recyclerItem) {

        try {
            MediaPlayer player = new MediaPlayer();

            fileName = viewModel.getAudioCard(recyclerItem).getAddress();
            Log.d("startPlaying", fileName);
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.d("startPlaying", "prepare() failed");
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

    public void showPopup(View anchorView) {

        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        TextInputLayout saveDescr = popupView.findViewById(R.id.note_description);
        Button saveButton = popupView.findViewById(R.id.save_button);
        saveButton.setOnClickListener((v) -> {
            String text = saveDescr.getEditText().getText().toString();
            viewModel.addAudioCard(text, fileName, "");
            popupWindow.dismiss();
        });
    }

}
