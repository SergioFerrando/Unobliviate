package com.example.pis_entrega1;

import android.widget.EditText;

import java.util.Date;

public class Recording extends Notes {

    public Recording(Long date, EditText name) {
        super(date, name);
        this.setContent("Audio Note");
        this.setType();
    }
}
