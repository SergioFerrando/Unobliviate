package com.example.pis_entrega1.Note;

import android.util.Log;

import com.example.pis_entrega1.Model.DatabaseAdapter;
import com.example.pis_entrega1.R;

import java.io.File;
import com.example.pis_entrega1.*;

/**
 * Method to store the data of a photo note and interact with it.
 */
public class Photo extends Notes {

    private String address;
    private String url;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    /**
     * Constructor method of the class.
     */
    public Photo(){
        super();
    }

    /**
     * Getter method of the attribute "url".
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method of the attribute "url".
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Method to save a text note inside the "DatabaseAdapter".
     */
    @Override
    public void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.savePhotoDocumentWithFile(this.getName(), address, this.getDate());
    }

    /**
     * Method to delete a note inside the "DatabaseAdapter".
     */
    @Override
    public void delete() {
        adapter.delete(this.getID());
    }

    /**
     * Method to refresh the data inside the "DatabaseAdapter".
     */
    public void modify() {
        adapter.actualizarPhotoNote(this.getName(), this.address, this.getID(), this.getDate(), this.url);
    }

    /**
     * Constructor method of the class with parameters.
     * @param name String
     * @param Adress String
     * @param id String
     * @param date String
     * @param url String
     */
    public Photo(String name, String Adress, String id, String date, String url) {
        super(name, id);
        this.url = url;
        File file = new File(Adress);
        if(!file.exists()){
            this.address = adapter.descargarPhotoDatabase(url);
        }else{
            this.address = Adress;
        }
        this.setDate(date);
    }

    /**
     * Constructor method of the class with parameters.
     * @param date String
     * @param name String
     * @param path String
     */
    public Photo(String date, String name, String path) {
        super(name);
        this.setDate(date);
        this.setAddress(path);
    }

    /**
     * Getter method of the attribute "address".
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method of the attribute "address".
     * @param Adress String
     */
    public void setAddress(String Adress){this.address = Adress;}

}
