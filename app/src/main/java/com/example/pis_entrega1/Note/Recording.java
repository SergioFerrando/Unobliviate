package com.example.pis_entrega1.Note;

import android.util.Log;

import com.example.pis_entrega1.Model.DatabaseAdapter;
import com.example.pis_entrega1.R;
import com.example.pis_entrega1.*;

import java.io.File;

/**
 * Class to store the data of an audio note and interact with it inheriting from the "Notes" class.
 */
public class Recording extends Notes {

    private String address;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    private String url;

    /**
     * Getter method of the attribute "url".
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method of the attribute "url".
     * @param url String
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Constructor method of the class.
     */
    public Recording(){
        super();
    }

    /**
     * Constructor method of the class with parameters.
     * @param date String
     * @param name String
     * @param address String
     */
    public Recording(String date, String name, String address) {
        super(name);
        this.setDate(date);
        this.address = address;
    }

    /**
     *
     */
    @Override
    public void delete() {
        adapter.delete(this.getID());
    }
    @Override
    public void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.saveAudioDocumentWithFile(this.getName(), this.address, this.getDate());
    }

    public Recording(String name, String address, String id, String date, String url) {
        super(name, id);
        this.url = url;
        File file = new File(address);
        if(!file.exists()){
            this.address = adapter.descargarAudioDatabase(url);
        }else{
            this.address = address;
        }
        this.setDate(date);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Adress){this.address = Adress;}

    public void modify() {
        adapter.actualizarAudioNote(this.getName(), this.address, this.getID(), this.getDate(), this.url);
    }
}
