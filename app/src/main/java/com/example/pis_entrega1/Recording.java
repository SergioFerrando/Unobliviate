package com.example.pis_entrega1;

import android.util.Log;
import android.widget.EditText;

public class Recording extends Notes {

    private String AudioTitle;
    private String address;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Recording(){
        super();
    }

    @Override
    void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.saveAudioDocumentWithFile(AudioTitle, address);
    }

    public Recording(Long date, String name, String address) {
        super(name);
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
