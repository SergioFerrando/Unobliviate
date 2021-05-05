package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TextNote extends AppCompatActivity implements View.OnClickListener{
    Text text = new Text();
    EditText title, content;
    int position = -1;
    boolean existente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        findViewById(R.id.TextSaveButton).setOnClickListener(this);
        findViewById(R.id.TextCheckList).setOnClickListener(this);
        findViewById(R.id.TextRememberButton).setOnClickListener(this);
        findViewById(R.id.TextShareButton).setOnClickListener(this);
        findViewById(R.id.TextDeleteButton).setOnClickListener(this);
        if (getIntent().getStringExtra("newTitleText") != null){
            title = this.findViewById(R.id.editTextTitleTextNote);
            content = this.findViewById(R.id.editTextTextNote);
            this.text.setName(getIntent().getStringExtra("newTitleText"));
            this.text.setText(getIntent().getStringExtra("newTextText"));
            this.position = getIntent().getIntExtra("positionText", 0);
            title.setText(this.text.getName());
            content.setText(this.text.getText());
            this.existente = true;
        }else{
            title = this.findViewById(R.id.editTextTitleTextNote);
            if (title != null){
                text.setName(title.getText().toString());
            }else{
                text.setName("");
            }
            content = this.findViewById(R.id.editTextTextNote);
            if (content.getText() != null){
                text.setText(content.getText().toString());
            }else{
                text.setText("");
            }
        }
    }

    public void goToShareIntent(){
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = ContactsContract.CommonDataKinds.Note.getText().toString();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Text Note");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent,"Share using... "));*/
    }

    public void goToRememberIntent(){
        DatePickerFragment calendar = new DatePickerFragment();
        calendar.show(getSupportFragmentManager(),"DatePicker");
        TimePickerFragment hour = new TimePickerFragment();
        hour.show(getSupportFragmentManager(),"HOUR");
    }

    public void goToMainIntent(){
        Intent intent = new Intent();
        intent.putExtra("positionDelete", this.position);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void CheckList(){
        text.setText(this.findViewById(R.id.editTextTextNote).toString());
        String temp = text.getText() + "\n -";
        text.setText(temp);
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextSaveButton == v.getId()){
            title = this.findViewById(R.id.editTextTitleTextNote);
            if (title.getText() != null){
                this.text.setName(title.getText().toString());
            }else{
                this.text.setName("");
            }
            content = this.findViewById(R.id.editTextTextNote);
            if (content.getText() != null){
                this.text.setText(content.getText().toString());
            }else{
                this.text.setText("");
            }
            Intent intent = new Intent();
            intent.putExtra("title", this.text.getName());
            intent.putExtra("text", this.text.getText());
            intent.putExtra("date", System.currentTimeMillis());
            intent.putExtra("positionText", this.position);
            setResult(RESULT_OK, intent);
            finish();
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