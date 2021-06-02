package com.example.pis_entrega1;

import android.util.Log;

import java.io.File;

/**
 * Class to store the data of an audio note and interact with it inheriting from the "Notes" class.
 */
public class Recording extends Notes {

    private String AudioTitle;
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
        AudioTitle = name;
        this.setDate(date);
        this.address = address;
        this.setContent("Audio Note");
        this.setType(R.drawable.micro);
    }

    /**
     *
     */
    @Override
    public void delete() {
        adapter.delete(this.getID());
    }
    @Override
    void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.saveAudioDocumentWithFile(this.AudioTitle, this.address, this.getDate());
    }

    public Recording(String name, String address, String id, String date, String url) {
        super(name, id);
        AudioTitle = name;
        this.url = url;
        File file = new File(address);
        if(!file.exists()){
            this.address = adapter.descargarAudioDatabase(url);
        }else{
            this.address = address;
        }
        this.setDate(date);
        this.setContent("Audio Note");
        this.setType(R.drawable.micro);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Adress){this.address = Adress;}

    public void modify() {
        adapter.actualizarAudioNote(this.getName(), this.address, this.getID(), this.getDate(), this.url);
    }
}
