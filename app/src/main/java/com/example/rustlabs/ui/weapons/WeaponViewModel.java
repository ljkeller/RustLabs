package com.example.rustlabs.ui.weapons;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeaponViewModel extends ViewModel
{

    private MutableLiveData<String> mText;

    public WeaponViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is the weapons fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}