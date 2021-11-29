package com.example.rustlabs.ui.structures;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StructuresViewModel extends ViewModel
{

    private MutableLiveData<String> mText;

    public StructuresViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}