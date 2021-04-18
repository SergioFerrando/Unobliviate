package com.example.pis_entrega1;
import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

import javax.*;

import com.example.pis_entrega1.*;

public class NotesContainer {

    ArrayList<Notes> container;

    public NotesContainer() {
        container = new ArrayList<Notes>();
    }

    void addTextNote(String title, String text){
        Date d = new Date();
        Text t = new Text(d.getTime(), title, text);

    }

    void addPhotoNote(String title, Image image){
        Date d = new Date();
        Photo p = new Photo(d.getTime(), title, image);
    }
}
