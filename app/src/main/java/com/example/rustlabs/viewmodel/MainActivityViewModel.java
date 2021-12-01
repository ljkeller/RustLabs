package com.example.rustlabs.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.rustlabs.Filters;

public class MainActivityViewModel extends ViewModel
{

    private Filters mFilters;

    public MainActivityViewModel() {
//        mFilters = Filters.getDefault();
    }

    public Filters getFilters() {
        return mFilters;
    }

    public void setFilters(Filters mFilters) {
        this.mFilters = mFilters;
    }
}
