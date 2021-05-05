package com.example.pis_entrega1;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainActivityViewModel extends ViewModel implements DatabaseAdapter.vmInterface{


    private final MutableLiveData<ArrayList<Notes>> mAudioCards;
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
    public LiveData<ArrayList<Notes>> getAudioCards(){
        return mAudioCards;
    }

    public Notes getNotesById(int idx){
        return mAudioCards.getValue().get(idx);
    }



    public LiveData<String> getToast(){
        return mToast;
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void setCollection(ArrayList<Notes> ac) {
        mAudioCards.setValue(ac);
    }

    public void setToast(String t) {
        mToast.setValue(t);
    }
}


