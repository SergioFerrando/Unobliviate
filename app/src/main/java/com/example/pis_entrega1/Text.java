package com.example.pis_entrega1;

import android.util.Log;

public class Text extends Notes{

    private String text;
    private String path;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Text(){
        super();
    }

    void modify(){
        adapter.actualizarTextNote(this.getName(), this.getText(),this.getDate(), this.path, this.getID());
    }

    @Override
    void saveNote() {
        Log.d("saveTextNote", "saveTextNote-> saveDocument");
        adapter.saveTextDocumentWithFile(this.getName(), this.getText(), this.getPath(), this.getDate());
    }

    @Override
    public void delete() {
        adapter.delete(this.getID());
    }

    public Text(String name, String bodyText, String Adress, String id){
        super(name, id);
        this.setPath(Adress);
        this.setName(name);
        this.setText(bodyText);
        this.setType(R.drawable.tex);
    }

    public Text(String date, String name, String text) {
        super(name);
        this.setText(text);
        this.setDate(date);
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
