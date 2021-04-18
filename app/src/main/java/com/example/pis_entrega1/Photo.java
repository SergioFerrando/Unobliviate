package com.example.pis_entrega1;

import android.media.Image;

import java.util.Date;

public class Photo extends Notes {
    private Image photo;

    public Photo(long date, String name, Image image) {
        super(date, name);
        this.setPhoto(image);
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }
}
