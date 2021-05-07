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

    void addTextNote(Text t, int position) {
        if (position == -1){
            this.container.add(t);
        }else{
            this.container.add(position, t);
            this.container.remove(position + 1);
        }
    }

    void addAudioNote(Recording recording, int positionAudio) {
        if (positionAudio == -1){
            this.container.add(recording);
        }else{
            this.container.add(positionAudio, recording);
            this.container.remove(positionAudio + 1);
        }
    }

    void addPhotoNote(Photo p) {
        this.container.add(p);
    }

    void deleteNote(int position){
        this.container.remove(position);
    }

    public ArrayList<Notes> getContainer() {
        return container;
    }

    public void setContainer(ArrayList<Notes> container) {
        this.container = container;
    }

    public Notes get(int p){return container.get(p);}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        Notes temp = this.getContainer().get(fromPosition);
        this.getContainer().remove(temp);
        this.getContainer().set(toPosition, temp);
    }
}
