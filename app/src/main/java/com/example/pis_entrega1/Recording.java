package com.example.pis_entrega1;

import android.widget.EditText;

public class Recording extends Notes {

    private String AudioTitle;
    private String address;

    public Recording(){
        super();
    }

    public Recording(Long date, String name, String address) {
        super(date, name);
        AudioTitle = name;
        this.address = address;
        this.setContent("Audio Note");
        this.setType(R.drawable.micro);
    }

    public String getAudioTitle() {
        return AudioTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAudioTitle(String audioTitle) {
        AudioTitle = audioTitle;
    }

    public void setAddress(String Adress){this.address = Adress;}
}
