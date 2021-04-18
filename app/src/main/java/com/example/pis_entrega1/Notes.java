package com.example.pis_entrega1;

import android.widget.EditText;

import java.util.Date;
import java.util.Random;

public class Notes {
    private Date date;
    private EditText name;
    private Date deathLine;
    private String id;

    public Notes(Long date, EditText name) {
        Date d = new Date(date * 1000);
        this.setDate(d);
        this.setName(name);
        this.setId();
    }

    public Date getDate() {
        return date;
    }

    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    public Date getDeathLine() {
        return deathLine;
    }

    public void setDeathLine(Date deathLine) {
        this.deathLine = deathLine;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId() {
        Random r = new java.util.Random ();
        String s = Long.toString (r.nextLong () & Long.MAX_VALUE, 36);
    }
}
