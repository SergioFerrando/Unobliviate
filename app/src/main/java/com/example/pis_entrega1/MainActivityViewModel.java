package com.example.pis_entrega1;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainActivityViewModel extends ViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<Notes>> mNotes;
    private final MutableLiveData<String> mToast;
    private DatabaseAdapter da;

    public static final String TAG = "ViewModel";

    public void LogOut(){
        da.logOut();
    }

    //Constructor
    public MainActivityViewModel(){
        ArrayList<Notes> n = new ArrayList<>();
        mNotes = new MutableLiveData<ArrayList<Notes>>();
        mNotes.setValue(n);
        mToast = new MutableLiveData<String>();
        da = DatabaseAdapter.databaseAdapter;
        da.setInterface(this);
        da.getCollection();
    }

    void deleteNote(int position){
        this.mNotes.getValue().remove(position);
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Notes>> getListNotes(){
        return mNotes;
    }

    public Notes getNotesById(int idx){
        return mNotes.getValue().get(idx);
    }

    public void modifyAudioNote(Recording a, int position) {
        this.mNotes.getValue().get(position).setName(a.getName());
        a.modify();
    }

    void modifyTextNote(Text t, int position){
        ((Text) this.mNotes.getValue().get(position)).setText(t.getText());
        this.mNotes.getValue().get(position).setName(t.getName());
        t.modify();
    }

    void addTextNote(Text t, int position) {
        if (position == -1){
            this.mNotes.getValue().add(t);
            this.mNotes.setValue(mNotes.getValue());
            t.saveNote();
        }else{
            this.mNotes.getValue().add(position, t);
            this.mNotes.getValue().remove(position + 1);
        }
    }

    void addAudioNote(Recording recording, int positionAudio) {
        if (positionAudio == -1){
            this.mNotes.getValue().add(recording);
            this.mNotes.setValue(mNotes.getValue());
            recording.saveNote();
        }else{
            this.mNotes.getValue().add(positionAudio, recording);
            this.mNotes.getValue().remove(positionAudio + 1);
        }
    }

    void addPhotoNote(Photo p) {
        this.mNotes.getValue().add(p);
        this.mNotes.setValue(mNotes.getValue());
        p.saveNote();
    }

    public LiveData<String> getToast(){
        return mToast;
    }
    //communicates user inputs and updates the result in the viewModel

    @Override
    public void setCollection(ArrayList<Notes> ac) {
        this.mNotes.setValue(ac);
    }

    public void setToast(String t) {
        mToast.setValue(t);
    }
}


