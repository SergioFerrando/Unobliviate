package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public NotesContainer nc = new NotesContainer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable extras = getIntent().getParcelableExtra("MyClass");
        if (extras != null) {
            nc = (NotesContainer) extras;
        }
        setContentView(R.layout.activity_main);
        findViewById(R.id.TextButton).setOnClickListener(this);
        findViewById(R.id.AudioButton).setOnClickListener(this);
        findViewById(R.id.CameraButton).setOnClickListener(this);
        this.fillTableLayout();
    }

    public void goToTextNote(){
        Intent i = new Intent(this, TextNote.class);
        i.putExtra("MyClass", nc);
        startActivity(i);
    }

    public void goTOAudioNote(){
        Intent i = new Intent(this, AudioNote.class);
        i.putExtra("MyClass", nc);
        startActivity(i);
    }

    public void goToCameraNote(){
        Intent i = new Intent(this, PhotoNote.class);
        i.putExtra("MyClass", nc);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(R.id.TextButton == v.getId()){
            goToTextNote();
        }
        if(R.id.AudioButton == v.getId()){
            goTOAudioNote();
        }
        if(R.id.CameraButton == v.getId()){
            goToCameraNote();
        }
    }

    void fillTableLayout(){
        TableLayout table = (TableLayout) this.findViewById(R.id.tableLayoutList);
        if(this.nc == null){
            if(!(this.nc.getContainer().size() == 0)){
                System.out.println(this.nc.getContainer().get(0).getId());
                for(int i = 0; i < this.nc.getContainer().size(); i++)
                {
                    TableRow row = (TableRow) this.findViewById(R.id.LinearLayoutRow);
                    ((TextView)row.findViewById(R.id.showTitle)).setText((CharSequence) this.nc.getContainer().get(i).getName());
                    ((TextView)row.findViewById(R.id.show_date)).setText((CharSequence) this.nc.getContainer().get(i).getDate());
                    table.addView(row);
                }
            }
        }
    }
}