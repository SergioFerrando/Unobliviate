package com.example.pis_entrega1;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Date;

public class Photo extends Notes {
    private String PhotoTitle;
    private String address;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    private String id;

    public Photo(){
        super();
    }

    @Override
    void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.savePhotoDocumentWithFile(PhotoTitle, address, this.getDate());
    }

    public void modify() {
        adapter.actualizarPhotoNote(this.getName(), this.address, this.id, this.getDate());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Photo(String name, String Adress, String id, String date) {
        super(name);
        this.PhotoTitle = name;
        this.address = Adress;
        this.id = id;
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

    public String getPhotoTitle() {
        return PhotoTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setPhotoTitle(String photoTitle) {
        PhotoTitle = photoTitle;
    }

    public void setAddress(String Adress){this.address = Adress;}

}
