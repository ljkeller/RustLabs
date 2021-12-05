package com.example.rustlabs.ui.armor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rustlabs.adapter.ArmorAdapter;
import com.example.rustlabs.adapter.WeaponAdapter;
import com.example.rustlabs.model.Armor;
import com.google.firebase.firestore.Query;

public class ArmorViewModel extends ViewModel
{

    private MutableLiveData<String> mText;

    private Query mQuery;
    private ArmorAdapter mAdapter;

    public ArmorViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is the armor fragment");
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

    public ArmorAdapter getAdapter()
    {
        return mAdapter;
    }

    public void setAdapter(ArmorAdapter mAdapter)
    {
        this.mAdapter = mAdapter;
    }

    public boolean hasQuery()
    {
        return mQuery != null;
    }
}