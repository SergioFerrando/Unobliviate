package com.example.pis_entrega1;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.EditText;

import java.util.Date;

public class Notes {
    private Date date;
    private String name;
    private Date deathLine;
    private String content;
    private int type;

    public Notes(){
        this.name = "";
        this.date = null;
    }

    public Notes(Long date, String name) {
        Date d = new Date(date * 1000);
        this.date = d;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(long date) {
        Date d = new Date(date * 1000);
        this.date = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeathLine() {
        return deathLine;
    }

    public void setDeathLine(Date deathLine) {
        this.deathLine = deathLine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
