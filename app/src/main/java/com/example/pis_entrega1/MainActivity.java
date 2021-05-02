package com.example.pis_entrega1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAdapter.ItemClickListener{
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;

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
        mRecyclerView.setAdapter(mAdapter);
        this.setTable();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (intent.getStringExtra("title") != null){
                    Text text_temp = new Text(intent.getLongExtra("date", 0), intent.getStringExtra("title"), intent.getStringExtra("text"));
                    this.nc.addTextNote(text_temp);
                    this.refreshTableNotes();
                } else if (intent.getStringExtra("title_audio_main") != null){
                    Recording recordingTemp = new Recording(intent.getLongExtra("date_audio_main", 0), intent.getStringExtra("title_audio_main"), intent.getStringExtra("Adress_main"));
                    this.nc.addAudioNote(recordingTemp);
                    this.refreshTableNotes();
                } else{
                    byte[] data = intent.getByteArrayExtra("byteImage_main");
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ImageView miniatura = null;
                    miniatura.post(new Runnable() {
                        @Override
                        public void run() {
                            miniatura.setImageBitmap(Bitmap.createScaledBitmap(bmp, miniatura.getWidth(), miniatura.getHeight(), false));
                        }
                    });
                    Photo photoTemp = new Photo(intent.getLongExtra("date_photo_main", 0), intent.getStringExtra("title_photo_main"), miniatura);
                    this.nc.addPhotoNote(photoTemp);
                }
                System.out.println(this.nc.getContainer().size());
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
        mAdapter = new MyAdapter(this, nc, this);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    void refreshTableNotes(){
        mAdapter.setmNotesContainer(this.nc);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter.getItem(position) instanceof Text){
            Intent i = new Intent(this, TextNote.class);
            i.putExtra("title_open_text", mAdapter.getItem(position).getName());
            i.putExtra("content_open_text", mAdapter.getItem(position).getContent());
            i.putExtra("date_open_text", mAdapter.getItem(position).getContent());
            startActivityForResult(i, 1);
        }
    }
}