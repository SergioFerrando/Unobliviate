package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TextNote extends AppCompatActivity implements View.OnClickListener{
    Text text = new Text();
    EditText title, content;
    int position = -1;
    boolean existente;
    String path;

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
            this.path = getIntent().getStringExtra("url");
            this.text.setId(getIntent().getStringExtra("id"));
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

    }

    public void goToMainIntent(){
        Intent intent = new Intent();
        intent.putExtra("positionDelete", this.position);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void CheckList(){
        String temp = (content.getText() + "\n -");
        content.setText(temp);
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextSaveButton == v.getId()){
            title = this.findViewById(R.id.editTextTitleTextNote);
            if (title.getText() != null) {
                this.text.setName(title.getText().toString());
            } else {
                this.text.setName("");
            }
            content = this.findViewById(R.id.editTextTextNote);
            if (content.getText() != null) {
                this.text.setText(content.getText().toString());
            } else {
                this.text.setText("");
            }
            if(path == null) {
                FileOutputStream fileOutputStream = null;

                try {
                    fileOutputStream = openFileOutput(title.getText().toString(), MODE_PRIVATE);
                    fileOutputStream.write(text.getText().toString().getBytes());
                    this.path = getFilesDir() + "/" + title.getText().toString();
                    Log.d("TAG1", "Fichero Salvado en: " + getFilesDir() + "/" + title.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("title", this.text.getName());
                intent.putExtra("text", this.text.getText());
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
                String date = df.format(Calendar.getInstance().getTime());
                intent.putExtra("date", date);
                intent.putExtra("positionText", this.position);
                intent.putExtra("path", this.path);
                setResult(RESULT_OK, intent);
                finish();
            }else{
                Intent intent = new Intent();
                intent.putExtra("title", this.text.getName());
                intent.putExtra("text", this.text.getText());
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
                String date = df.format(Calendar.getInstance().getTime());
                intent.putExtra("date", date);
                intent.putExtra("id", this.text.getId());
                intent.putExtra("positionText", this.position);
                intent.putExtra("path", this.path);
                setResult(5, intent);
                finish();
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
}