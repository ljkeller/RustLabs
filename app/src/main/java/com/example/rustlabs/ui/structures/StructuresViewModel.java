package com.example.rustlabs.ui.structures;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rustlabs.adapter.ArmorAdapter;
import com.example.rustlabs.adapter.StructureAdapter;
import com.google.firebase.firestore.Query;

public class StructuresViewModel extends ViewModel
{

    private MutableLiveData<String> mText;

    private Query mQuery;
    private StructureAdapter mAdapter;

    public StructuresViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is the structures fragment");
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

    public StructureAdapter getAdapter()
    {
        return mAdapter;
    }

    public void setAdapter(StructureAdapter mAdapter)
    {
        this.mAdapter = mAdapter;
    }

    public boolean hasQuery()
    {
        return mQuery != null;
    }
}