package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class TextNote extends AppCompatActivity implements View.OnClickListener{
    EditText Title;
    EditText Note;
    private NotesContainer container = new NotesContainer();

    public NotesContainer getContainer() {
        return container;
    }

    public void setContainer(NotesContainer container) {
        this.container = container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContainer((NotesContainer) getIntent().getParcelableExtra("MyClass"));
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
        startActivity(n);
    }

    public void CheckList(){
        Note = this.findViewById(R.id.editTextTextNote);
        Note.setText(Note.getText().toString() + "\n -");
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextSaveButton == v.getId()){
            try {
                Title = this.findViewById(R.id.editTextTitleTextNote);
                Note = this.findViewById(R.id.editTextTextNote);
                this.container.addTextNote(Title, Note);

            } catch (Exception e) {

            }
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
    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MyClass", (Parcelable) container);
        startActivity(i);
        finish();
    }
}