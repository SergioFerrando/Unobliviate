package com.example.pis_entrega1;

import android.util.Log;

/**
 * Class to store the data of a text note and interact with it inheriting from the "Notes" class.
 */
public class Text extends Notes{

    private String text;
    private String path;
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
        adapter.actualizarTextNote(this.getName(), this.getText(),this.getDate(), this.path, this.getID());
    }

    /**
     * Method to save a text note inside the "DatabaseAdapter".
     */
    @Override
    void saveNote() {
        Log.d("saveTextNote", "saveTextNote-> saveDocument");
        adapter.saveTextDocumentWithFile(this.getName(), this.getText(), this.getPath(), this.getDate());
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
     * @param Adress String
     * @param id String
     */
    public Text(String name, String bodyText, String Adress, String id){
        super(name, id);
        this.setPath(Adress);
        this.setName(name);
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
     * Getter method of the attribute "path".
     * @return "path"
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter method of the attribute "path".
     * @param path String
     */
    public void setPath(String path) {
        this.path = path;
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
