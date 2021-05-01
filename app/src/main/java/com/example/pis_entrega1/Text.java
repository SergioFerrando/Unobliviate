package com.example.pis_entrega1;

import android.graphics.drawable.Drawable;
import android.widget.EditText;

import java.util.Date;

public class Text extends Notes{

    private String text;

    public Text(){
        super();
    }

    public Text(long date, String name, String text) {
        super(date, name);
        this.setText(text);
        this.setContent("Text Note");
        this.setType(R.drawable.tex);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
