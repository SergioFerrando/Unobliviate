package com.example.pis_entrega1;

import java.util.Date;

public class Text extends Notes{

    private String text;

    public Text(long date, String name, String text) {
        super(date, name);
        this.setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
