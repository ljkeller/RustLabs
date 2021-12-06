package com.example.rustlabs.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rustlabs.R;

public class SearchViewModel extends ViewModel
{

    private MutableLiveData<String> mText;

    public SearchViewModel()
    {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}