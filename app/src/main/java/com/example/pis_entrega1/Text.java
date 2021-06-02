package com.example.pis_entrega1;

import android.util.Log;

/**
 * Class to store the data of a text note and interact with it inheriting from the "Notes" class.
 */
public class Text extends Notes{

    private String text;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    /**
     * Constructor method of the class
     */
    public Text(){
        super();
    }

    /**
     * Method to refresh the data inside the "DatabaseAdapter".
     */
    void modify(){
        adapter.actualizarTextNote(this.getName(), this.getText(),this.getDate(), this.getID());
    }

    /**
     * Method to save a text note inside the "DatabaseAdapter".
     */
    @Override
    void saveNote() {
        Log.d("saveTextNote", "saveTextNote-> saveDocument");
        adapter.saveTextDocument(this.getName(), this.getText(), this.getDate());
    }

    /**
     * Method to delete a note inside the "DatabaseAdapter".
     */
    @Override
    public void delete() {
        adapter.delete(this.getID());
    }

    /**
     * Constructor method of the class with parameters.
     * @param name String
     * @param bodyText String
     * @param date String
     * @param id String
     */
    public Text(String name, String bodyText, String date, String id){
        super(name, id);
        this.setName(name);
        this.setDate(date);
        this.setText(bodyText);
        this.setType(R.drawable.tex);
    }

    /**
     * Constructor method of the class with parameters.
     * @param date String
     * @param name String
     * @param text String
     */
    public Text(String date, String name, String text) {
        super(name);
        this.setText(text);
        this.setDate(date);
        this.setContent("Text Note");
        this.setType(R.drawable.tex);
    }

    /**
     * Getter method of the attribute "text".
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Setter method of the attribute "text".
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
    }

}
