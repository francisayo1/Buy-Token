package com.feedco.note.ViewModel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompanySelectorViewModel extends ViewModel {

    MutableLiveData<String> mutableLiveData=new MutableLiveData <> (  );

    public LiveData <String> getCompanyName(){
       return mutableLiveData;
    }

    public void setCompanyName(String name){
        mutableLiveData.setValue ( name );
    }


}
