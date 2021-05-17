package com.example.pis_entrega1;

import android.util.Log;
import android.widget.EditText;

public class Recording extends Notes {

    private String AudioTitle;
    private String address;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    private String id;

    public Recording(){
        super();
    }
    public Recording(Long Date, String name, String address) {
        super(name);
        AudioTitle = name;
        this.address = address;
        this.setContent("Audio Note");
        this.setType(R.drawable.micro);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    void saveNote() {
        Log.d("saveAudioNote", "saveAudioNote-> saveDocument");
        adapter.saveAudioDocumentWithFile(AudioTitle, address);
    }

    public Recording(String name, String address, String id) {
        super(name);
        AudioTitle = name;
        this.address = address;
        this.id = id;
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

    public void modify() {
        adapter.actualizarAudioNote(this.getName(), this.address, this.id);
    }
}
