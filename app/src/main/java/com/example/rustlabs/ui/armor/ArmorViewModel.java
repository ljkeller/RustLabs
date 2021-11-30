package com.example.rustlabs.ui.armor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArmorViewModel extends ViewModel
{

    private MutableLiveData<String> mText;

    public ArmorViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is the armor fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}