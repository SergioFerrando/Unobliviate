package com.example.pis_entrega1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAdapter.ItemClickListener{
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public NotesContainer nc;
    public Context parentContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nc = new NotesContainer();
        Text text = new Text();
        text.setName("penepenepene");
        nc.addTextNote(text);
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
                } else if (intent.getStringExtra("title_audio") != null){
                    Recording recordingTemp = new Recording(intent.getLongExtra("date_audio", 0), intent.getStringExtra("title_audio"), intent.getStringExtra("Adress"));
                    this.nc.addAudioNote(recordingTemp);
                    this.refreshTableNotes();
                } else{
                    //Photo photo = new Photo
                }
                System.out.println(this.nc.getContainer().size());
            }
        }
    }

    public void goToTextNote() {
        Intent i = new Intent(this, TextNote.class);
        i.putExtra("MyClass", nc);
        startActivityForResult(i, 1);
    }

    public void goTOAudioNote() {
        Intent i = new Intent(this, AudioNote.class);
        i.putExtra("MyClass", nc);
        startActivity(i);
    }

    public void goToCameraNote() {
        Intent i = new Intent(this, PhotoNote.class);
        i.putExtra("MyClass", nc);
        startActivity(i);
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
        mAdapter.setClickListener((MyAdapter.ItemClickListener) this);
        recyclerView.setAdapter(mAdapter);
    }

    void refreshTableNotes(){
        mAdapter.setmNotesContainer(this.nc);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}