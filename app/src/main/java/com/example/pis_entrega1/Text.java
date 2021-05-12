package com.example.pis_entrega1;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Text extends Notes{

    private String text;
    private String path;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Text(){
        super();
    }

    @Override
    void saveNote() {
        Log.d("saveTextNote", "saveTextNote-> saveDocument");
        adapter.saveTextDocumentWithFile(this.getName(), this.getText(), this.getPath());
    }
    public Text(String name, String bodyText, String Adress){
        super(name);
        this.setPath(Adress);
        this.setName(name);
        this.setText(bodyText);
        this.setType(R.drawable.tex);
    }
    public Text(long date, String name, String text) {
        super(name);
        this.setText(text);
        this.setContent("Text Note");
        this.setType(R.drawable.tex);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
