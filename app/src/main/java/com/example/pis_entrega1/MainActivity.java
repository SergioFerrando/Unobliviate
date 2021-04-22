package com.example.pis_entrega1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public NotesContainer nc = new NotesContainer();
    public Context parentContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotesContainer extras = getIntent().getExtras().getParcelable("MyClass");
        if (extras != null) {
            this.nc = extras;
        }
        parentContext = this.getBaseContext();
        setContentView(R.layout.activity_main);
        findViewById(R.id.TextButton).setOnClickListener(this);
        findViewById(R.id.AudioButton).setOnClickListener(this);
        findViewById(R.id.CameraButton).setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(this.nc, this);
        mRecyclerView.setAdapter(mAdapter);

        setLiveDataObservers();
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        final androidx.lifecycle.Observer<NotesContainer> observer = new androidx.lifecycle.Observer<NotesContainer>() {
            @Override
            public void onChanged(NotesContainer notesContainer) {
                MyAdapter myAdapter = new MyAdapter(notesContainer, parentContext);
                mRecyclerView.swapAdapter(myAdapter, false);
                myAdapter.notifyDataSetChanged();
            }
        };
    }

    public void goToTextNote() {
        Intent i = new Intent(this, TextNote.class);
        i.putExtra("MyClass", nc);
        startActivity(i);
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



    /*void fillTableLayout() {
        TableLayout table = (TableLayout) this.findViewById(R.id.tableLayoutList);
        if (this.nc == null) {
            if (!(this.nc.getContainer().size() == 0)) {
                System.out.println(this.nc.getContainer().get(0).getId());
                for (int i = 0; i < this.nc.getContainer().size(); i++) {
                    TableRow row = (TableRow) this.findViewById(R.id.LinearLayoutRow);
                    ((TextView) row.findViewById(R.id.showTitle)).setText((CharSequence) this.nc.getContainer().get(i).getName());
                    ((TextView) row.findViewById(R.id.show_date)).setText((CharSequence) this.nc.getContainer().get(i).getDate());
                    table.addView(row);
                }
            }
        }
    }*/
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted) finish();
    }*/



}