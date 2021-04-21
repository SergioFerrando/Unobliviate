package com.example.pis_entrega1;

import android.widget.EditText;

import java.util.Date;

public class Text extends Notes{

    private EditText text;

    public Text(long date, EditText name, EditText text) {
        super(date, name);
        this.setText(text);
        this.setContent("Text Note");
        this.setType();
    }

    public EditText getText() {
        return text;
    }

    public void setText(EditText text) {
        this.text = text;
    }
}
