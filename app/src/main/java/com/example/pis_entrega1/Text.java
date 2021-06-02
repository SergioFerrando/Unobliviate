package com.example.pis_entrega1;

import android.util.Log;

public class Text extends Notes{

    private String text;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Text(){
        super();
    }

    void modify(){
        adapter.actualizarTextNote(this.getName(), this.getText(),this.getDate(), this.getID());
    }

    @Override
    void saveNote() {
        Log.d("saveTextNote", "saveTextNote-> saveDocument");
        adapter.saveTextDocument(this.getName(), this.getText(), this.getDate());
    }

    @Override
    public void delete() {
        adapter.delete(this.getID());
    }

    public Text(String name, String bodyText,String Date, String id){
        super(name, id);
        this.setName(name);
        this.setDate(Date);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
