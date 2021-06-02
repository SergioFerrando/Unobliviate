package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        findViewById(R.id.TextShareButton).setOnClickListener(this);
        findViewById(R.id.TextDeleteButton).setOnClickListener(this);
        title = this.findViewById(R.id.editTextTitleTextNote);
        content = this.findViewById(R.id.editTextTextNote);
        if (getIntent().getStringExtra("newTitleText") != null){
            this.text.setName(getIntent().getStringExtra("newTitleText"));
            this.text.setText(getIntent().getStringExtra("newTextText"));
            this.path = getIntent().getStringExtra("url");
            this.text.setID(getIntent().getStringExtra("id"));
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
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content.getText().toString());
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent,text.getName());
        startActivity(shareIntent);
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
            if (title.getText().toString() != null) {
                this.text.setName(title.getText().toString());
            } else {
                this.text.setName("");
            }
            if (content.getText().toString() != null) {
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
                    Log.d("TAG1", "Fichero Salvado en: " + getFilesDir() + "/" + title.getText()+ ".txt");
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
                intent.putExtra("id", this.text.getID());
                intent.putExtra("positionText", this.position);
                intent.putExtra("path", this.path);
                setResult(5, intent);
                finish();
            }
        }
        if (R.id.TextCheckList == v.getId()){
            CheckList();
        }
        if (R.id.TextShareButton == v.getId()){
            goToShareIntent();
        }
        if (R.id.TextDeleteButton == v.getId()){
            goToMainIntent();
        }

    }
}