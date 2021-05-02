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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;

    private MyAdapter mAdapter;

    public NotesContainer nc;
    public Context parentContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nc = new NotesContainer();
        parentContext = this.getBaseContext();
        setContentView(R.layout.activity_main);
        findViewById(R.id.TextButton).setOnClickListener(this);
        findViewById(R.id.AudioButton).setOnClickListener(this);
        findViewById(R.id.CameraButton).setOnClickListener(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        this.setTable();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (intent.getStringExtra("title") != null){
                    Text text_temp = new Text(intent.getLongExtra("date", 0), intent.getStringExtra("title"), intent.getStringExtra("text"));
                    this.nc.addTextNote(text_temp);
                    this.setTable();
                } else if (intent.getStringExtra("title_audio_main") != null){
                    Recording recordingTemp = new Recording(intent.getLongExtra("date_audio_main", 0), intent.getStringExtra("title_audio_main"), intent.getStringExtra("Adress_main"));
                    this.nc.addAudioNote(recordingTemp);
                    this.setTable();
                } else{
                    Photo photoTemp = new Photo(intent.getLongExtra("date_photo_main", 0), intent.getStringExtra("title_photo_main"), intent.getByteArrayExtra("byteImage_main"));
                    this.nc.addPhotoNote(photoTemp);
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
        mAdapter = new MyAdapter(this, nc);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Selección: "+ nc.get(recyclerView.getChildAdapterPosition(v)).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    /*@Override
    public void onItemClick(View view, int position) {
        if (mAdapter.getItem(position) instanceof Text){
            Intent i = new Intent(this, TextNote.class);
            i.putExtra("title_open_text", mAdapter.getItem(position).getName());
            i.putExtra("content_open_text", mAdapter.getItem(position).getContent());
            i.putExtra("date_open_text", mAdapter.getItem(position).getContent());
            startActivityForResult(i, 1);
        }
    }*/
}