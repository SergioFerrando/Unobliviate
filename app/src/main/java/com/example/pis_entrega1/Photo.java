package com.example.pis_entrega1;

import android.util.Log;

import java.io.File;

public class Photo extends Notes {
    private String PhotoTitle;
    private String address;
    private String url;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    public Photo(){
        super();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.savePhotoDocumentWithFile(PhotoTitle, address, this.getDate());
    }

    @Override
    public void delete() {
        adapter.delete(this.getID());
    }

    public void modify() {
        adapter.actualizarPhotoNote(this.getName(), this.address, this.getID(), this.getDate(), this.url);
    }

    public Photo(String name, String Adress, String id, String date, String url) {
        super(name, id);
        this.PhotoTitle = name;
        this.url = url;
        File file = new File(Adress);
        if(!file.exists()){
            this.address = adapter.descargarPhotoDatabase(url);
        }else{
            this.address = Adress;
        }
        this.setDate(date);
        this.setContent("Photo Note");
        this.setType(R.drawable.camara);
    }

    public Photo(String date, String name, String path) {
        super(name);
        this.setDate(date);
        this.setAddress(path);
        this.PhotoTitle = name;
        this.setContent("Photo Note");
        this.setType(R.drawable.camara);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Adress){this.address = Adress;}

}
