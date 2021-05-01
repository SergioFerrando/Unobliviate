package com.example.pis_entrega1;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class NotesContainer implements Parcelable {
    ArrayList<Notes> container = new ArrayList<>();

    public NotesContainer() {
        container = new ArrayList<>();
    }

    public NotesContainer(ArrayList<Notes> container){
        this.container = container;
    }

    protected NotesContainer(Parcel in) {
    }

    public static final Creator<NotesContainer> CREATOR = new Creator<NotesContainer>() {
        @Override
        public NotesContainer createFromParcel(Parcel in) {
            return new NotesContainer(in);
        }

        @Override
        public NotesContainer[] newArray(int size) {
            return new NotesContainer[size];
        }
    };

    void addTextNote(Text t) {
        this.container.add(t);
    }

    void addAudioNote(String title, String Adress) {
        Date d = new Date();
        Recording t = new Recording(d.getTime(),title,Adress);
        this.container.add(t);
        System.out.println(this.getContainer().get(0));
    }

    void addPhotoNote(String title, Photo p) throws Exception {
        Date d = new Date();
        this.container.add(p);
    }

    private boolean exists(EditText name) {
        for (int i = 0; i < container.size(); i++){
            if (this.container.get(i).getName().equals(name)){
                System.out.println("false");
                return false;
            }
        }
        System.out.println("true");
        return true;
    }

    public ArrayList<Notes> getContainer() {
        return container;
    }

    public void setContainer(ArrayList<Notes> container) {
        this.container = container;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
