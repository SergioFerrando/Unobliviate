package com.example.pis_entrega1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;

    private MyAdapter mAdapter;

    public Context parentContext;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentContext = this.getBaseContext();
        viewModel = new MainActivityViewModel();
        setContentView(R.layout.activity_main);
        findViewById(R.id.TextButton).setOnClickListener(this);
        findViewById(R.id.AudioButton).setOnClickListener(this);
        findViewById(R.id.CameraButton).setOnClickListener(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        //this.setTable();
        //setLiveDataObservers();
    }
    /*
    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this, null).get(MainActivityViewModel.class);

        final Observer<ArrayList<Notes>> observer = new Observer<ArrayList<Notes>>() {
            @Override
            public void onChanged(ArrayList<Notes> ac) {
                MyAdapter newAdapter = new MyAdapter(parentContext, ac);
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
        viewModel.getListNotes().observe(this, observer);
        viewModel.getToast().observe(this, observerToast);
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (intent.getStringExtra("title") != null) {
                    Text text_temp = new Text(intent.getLongExtra("date", 0), intent.getStringExtra("title"), intent.getStringExtra("text"));
                    text_temp.setPath(intent.getStringExtra("path"));
                    this.viewModel.addTextNote(text_temp, intent.getIntExtra("positionText", -1));
                    this.setTable();
                } else if (intent.getStringExtra("title_audio_main") != null) {
                    Recording recordingTemp = new Recording(intent.getLongExtra("date_audio_main", 0), intent.getStringExtra("title_audio_main"), intent.getStringExtra("Adress_main"));
                    this.viewModel.addAudioNote(recordingTemp, intent.getIntExtra("positionAudio", -1));
                    this.setTable();
                } else if (intent.getStringExtra("title_photo_main") != null) {
                    Photo photoTemp = new Photo(intent.getLongExtra("date_photo_main", 0), intent.getStringExtra("title_photo_main"), intent.getByteArrayExtra("byteImage_main"));
                    this.viewModel.addPhotoNote(photoTemp);
                    this.setTable();
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (intent.getIntExtra("positionDelete", -1) != -1){
                    this.viewModel.deleteNote(intent.getIntExtra("positionDelete", -1));
                    this.setTable();
                }
            }
        }
    }

    public void goToTextNote() {
        Intent i = new Intent(this, TextNote.class);
        startActivityForResult(i, 1);
    }

    public void goTOAudioNote() {
        Intent i = new Intent(this, AudioNote.class);
        startActivityForResult(i, 1);
    }

    public void goToCameraNote() {
        Intent i = new Intent(this, PhotoNote.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextButton == v.getId()) {
            goToTextNote();
        }
        if (R.id.AudioButton == v.getId()) {
            goTOAudioNote();
        }
        if (R.id.CameraButton == v.getId()) {
            goToCameraNote();
        }
    }

    void setTable () {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, viewModel.getListNotes().getValue());
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getNotesById(recyclerView.getChildAdapterPosition(v)) instanceof Text){
                    passDataText((Text) viewModel.getNotesById(recyclerView.getChildAdapterPosition(v)), recyclerView.getChildAdapterPosition(v));
                }else if(viewModel.getNotesById(recyclerView.getChildAdapterPosition(v)) instanceof Recording){
                    passDataAudio((Recording) viewModel.getNotesById(recyclerView.getChildAdapterPosition(v)), recyclerView.getChildAdapterPosition(v));
                }else{
                    passDataPhoto((Photo) viewModel.getNotesById(recyclerView.getChildAdapterPosition(v)), recyclerView.getChildAdapterPosition(v));
                }
                Toast.makeText(getApplicationContext(),"Selección: "+ viewModel.getNotesById(recyclerView.getChildAdapterPosition(v)).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    void passDataText (Text text, int position) {
        Intent i = new Intent(this, TextNote.class);
        i.putExtra("newTitleText", text.getName());
        i.putExtra("newTextText", text.getText());
        i.putExtra("positionText", position);
        startActivityForResult(i, 1);
    }

    void passDataAudio (Recording recording, int position){
        Intent n1 = new Intent(this, AudioRecorded.class);
        n1.putExtra("newTitleAudio", recording.getName());
        n1.putExtra("Adress", recording.getAddress());
        n1.putExtra("positionAudio", position);
        startActivityForResult(n1, 1);
    }

    void passDataPhoto (Photo photo, int position){
        Intent n = new Intent(this, PhotoTaken.class);
        n.putExtra("newTitlePhoto", photo.getName());
        n.putExtra("photo", photo.miniatura);
        n.putExtra("positionPhoto", position);
        startActivityForResult(n, 1);
    }
}