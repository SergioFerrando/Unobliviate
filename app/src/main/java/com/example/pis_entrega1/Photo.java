package com.example.pis_entrega1;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Date;

public class Photo extends Notes {
    private Image photo;
    private String PhotoTitle;
    private String address;
    public byte[] miniatura;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    private String id;

    public Photo(){
        super();
    }

    @Override
    void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.savePhotoDocumentWithFile(PhotoTitle, address);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Photo(String name, String Adress, String id) {
        super(name);
        this.PhotoTitle = name;
        this.address = Adress;
        this.id = id;
        this.setContent("Photo Note");
        this.setType(R.drawable.camara);
    }
    public Photo(long date, String name, byte[] image) {
        super(name);
        this.setMiniatura(image);
        this.PhotoTitle = name;
        this.setContent("Photo Note");
        this.setType(R.drawable.camara);
    }

    public byte[] getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(byte[] miniatura) {
        this.miniatura = miniatura;
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

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }
}
