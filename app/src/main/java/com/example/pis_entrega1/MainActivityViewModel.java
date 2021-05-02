package com.example.pis_entrega1;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainActivityViewModel extends ViewModel implements DatabaseAdapter.vmInterface{


    private final MutableLiveData<ArrayList<AudioCard>> mAudioCards;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModel";

    //Constructor
    public MainActivityViewModel(){

        mAudioCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da= new DatabaseAdapter(this);
        da.getCollection();

    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<AudioCard>> getAudioCards(){
        return mAudioCards;
    }
    public AudioCard getAudioCard(int idx){
        return mAudioCards.getValue().get(idx);
    }
    public void addAudioCard(String description, String localPath, String owner){
        AudioCard ac = new AudioCard(description, localPath, owner);
        mAudioCards.getValue().add(ac);
        // Inform observer.
        mAudioCards.setValue(mAudioCards.getValue());
        ac.saveCard();

    }

    public LiveData<String> getToast(){
        return mToast;
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void setCollection(ArrayList<AudioCard> ac) {
        mAudioCards.setValue(ac);
    }

    public void setToast(String t) {
        mToast.setValue(t);
    }
}