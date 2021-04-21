package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class TextNote extends AppCompatActivity implements View.OnClickListener{
    EditText Title,Note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        findViewById(R.id.TextSaveButton).setOnClickListener(this);
        findViewById(R.id.TextCheckList).setOnClickListener(this);
        findViewById(R.id.TextRememberButton).setOnClickListener(this);
        findViewById(R.id.TextShareButton).setOnClickListener(this);
        findViewById(R.id.TextDeleteButton).setOnClickListener(this);
        Title = this.findViewById(R.id.editTextTitleTextNote);
        Note = this.findViewById(R.id.editTextTextNote);
    }

    public void goToShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = Note.getText().toString();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Text Note");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent,"Share using... "));
    }

    public void goToRememberIntent(){
        DatePickerFragment calendar = new DatePickerFragment();
        calendar.show(getSupportFragmentManager(),"DatePicker");
        TimePickerFragment hour = new TimePickerFragment();
        hour.show(getSupportFragmentManager(),"HOUR");
    }

    public void goToMainIntent(){
        Intent n = new Intent(this, MainActivity.class);
        Text nueva = new Text(null, Title.getText().toString(), Note.getText().toString());
        startActivity(n);
    }

    public void CheckList(){
        Note = this.findViewById(R.id.editTextTextNote);
        Note.setText(Note.getText().toString() + "\n -");
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextSaveButton == v.getId()){
            Intent n = new Intent(this, MainActivity.class);
            Text nueva = new Text(null, Title.getText().toString(), Note.getText().toString());
            startActivity(n);
        }
        if (R.id.TextCheckList == v.getId()){
            CheckList();
        }
        if (R.id.TextRememberButton == v.getId()){
            goToRememberIntent();
        }
        if (R.id.TextShareButton == v.getId()){
            goToShareIntent();
        }
        if (R.id.TextDeleteButton == v.getId()){
            goToMainIntent();
        }

    }
}