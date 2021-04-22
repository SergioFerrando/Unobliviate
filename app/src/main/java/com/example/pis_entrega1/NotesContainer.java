package com.example.pis_entrega1;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

public class NotesContainer implements Parcelable {

    ArrayList<Notes> container;

    public NotesContainer() {
        container = new ArrayList<>();
    }

    protected NotesContainer(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(container);
    }

    @Override
    public int describeContents() {
        return 0;
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

    void addTextNote(EditText title, String text) {
        Date d = new Date();
        Text t = new Text(d.getTime(), title, text);
        this.container.add(t);
        System.out.println(this.getContainer().get(0));
    }

    void addAudioNote(EditText title, String Adress) {
        Date d = new Date();
        Recording t = new Recording(d.getTime(),title,Adress);
        this.container.add(t);
        System.out.println(this.getContainer().get(0));
    }

    void addPhotoNote(EditText title, Image image) throws Exception {
        Date d = new Date();
        Photo p = new Photo(d.getTime(), title, image);
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
}
