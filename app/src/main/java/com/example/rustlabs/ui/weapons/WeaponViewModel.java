package com.example.rustlabs.ui.weapons;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rustlabs.adapter.WeaponAdapter;
import com.google.firebase.firestore.Query;

public class WeaponViewModel extends ViewModel
{

    private MutableLiveData<String> mText;

    private Query mQuery;
    private WeaponAdapter mAdapter;

    public WeaponViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is the weapons fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }

    public Query getQuery()
    {
        return mQuery;
    }

    public void setQuery(Query mQuery)
    {
        this.mQuery = mQuery;
    }

    public WeaponAdapter getAdapter()
    {
        return mAdapter;
    }

    public void setAdapter(WeaponAdapter mAdapter)
    {
        this.mAdapter = mAdapter;
    }

    public boolean hasQuery()
    {
        return mQuery != null;
    }
}