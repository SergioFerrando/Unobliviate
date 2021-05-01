package com.example.pis_entrega1;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.EditText;

import java.util.Date;

public class Photo extends Notes {
    private Image photo;
    private String PhotoTitle;
    private String address;
    private Bitmap miniatura;

    public Photo(){
        super();
    }

    public Photo(long date, String name, Image image) {
        super(date, name);
        this.setPhoto(image);
        this.PhotoTitle = name;
        this.setContent("Photo Note");
        this.setType(R.drawable.camara);
    }

    public Bitmap getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(Bitmap miniatura) {
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
