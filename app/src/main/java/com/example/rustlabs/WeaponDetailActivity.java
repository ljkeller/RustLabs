package com.example.rustlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.EventListener;

public class WeaponDetailActivity extends AppCompatActivity implements View.OnClickListener,
        EventListener<DocumentSnapshot>
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_detail);
    }
}