package com.example.pis_entrega1;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Recording extends Notes {

    private String AudioTitle;
    private String address;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Recording(){
        super();
    }

    public Recording(String date, String name, String address) {
        super(name);
        AudioTitle = name;
        this.setDate(date);
        this.address = address;
        this.setContent("Audio Note");
        this.setType(R.drawable.micro);
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

    public String getAudioTitle() {
        return AudioTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAudioTitle(String audioTitle) {
        AudioTitle = audioTitle;
    }

    public void setAddress(String Adress){this.address = Adress;}

    public void modify() {
        adapter.actualizarAudioNote(this.getName(), this.address, this.getID(), this.getDate(), this.url);
    }
}
