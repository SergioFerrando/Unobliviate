package com.example.pis_entrega1;


import java.util.ArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Class that contains the Array List of Notes
 */
public class MainActivityViewModel extends ViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<Notes>> mNotes;
    private final MutableLiveData<String> mToast;
    private DatabaseAdapter da;

    /**
     * Constructor of the class
     */
    public MainActivityViewModel(){
        ArrayList<Notes> n = new ArrayList<>();
        mNotes = new MutableLiveData<ArrayList<Notes>>();
        mNotes.setValue(n);
        mToast = new MutableLiveData<String>();
        da = DatabaseAdapter.databaseAdapter;
        da.setInterface(this);
        da.getCollection();
    }

    /**
     * Method to delete a note from the Array list of Notes
     * @param id Id of the note to delete
     */
    void deleteNote(Notes id){
        this.mNotes.getValue().remove(id);
        id.delete();
    }

    /**
     * Method that returns the Live Data Array List of Notes
     * @return Live Data Array List of Notes
     */
    public LiveData<ArrayList<Notes>> getListNotes(){
        return mNotes;
    }

    /**
     * Method that returns the note with the id given by the user
     * @param idx Index of note that you want to get
     * @return Note with this id
     */
    public Notes getNotesById(int idx){
        return mNotes.getValue().get(idx);
    }

    /**
     * Method to modify an Audio Note
     * @param a Recording with the changes in the Attributes
     * @param position Position of the note to modify
     */
    public void modifyAudioNote(Recording a, int position) {
        this.mNotes.getValue().get(position).setName(a.getName());
        this.mNotes.getValue().get(position).setDate(a.getDate());
        a.modify();
    }

    /**
     * Method to modify a Photo Note
     * @param p Photo with the changes in the Attributes
     * @param position Position of the note to modify
     */
    public void modifyPhotoNote(Photo p, int position) {
        this.mNotes.getValue().get(position).setName(p.getName());
        this.mNotes.getValue().get(position).setDate(p.getDate());
        p.modify();
    }

    /**
     * Method to modify a Text Note
     * @param t Text with the changes in the Attributes
     * @param position Position of the note to modify
     */
    void modifyTextNote(Text t, int position){
        ((Text) this.mNotes.getValue().get(position)).setText(t.getText());
        this.mNotes.getValue().get(position).setName(t.getName());
        this.mNotes.getValue().get(position).setDate(t.getDate());
        t.modify();
    }

    /**
     * Method to add a new Text note to the Array List
     * @param t Text to add
     */
    void addTextNote(Text t) {
        this.mNotes.getValue().add(t);
        this.mNotes.setValue(mNotes.getValue());
        t.saveNote();
    }

    /**
     * Method to add an Audio Note
     * @param recording recording to add in Array list
     */
    void addAudioNote(Recording recording) {
        this.mNotes.getValue().add(recording);
        this.mNotes.setValue(mNotes.getValue());
        recording.saveNote();
    }

    /**
     * Method to add a Photo note
     * @param p Photo Note to Add in array list
     */
    void addPhotoNote(Photo p) {
        this.mNotes.getValue().add(p);
        this.mNotes.setValue(mNotes.getValue());
        p.saveNote();
    }

    /**
     * Method that return mToast
     * @return mToast
     */
    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setCollection(ArrayList<Notes> ac) {
        this.mNotes.setValue(ac);
    }
}


