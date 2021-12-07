package com.example.rustlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.rustlabs.model.Tip;
import com.example.rustlabs.model.Weapon;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class WeaponDetailActivity extends AppCompatActivity implements View.OnClickListener,
        EventListener<DocumentSnapshot>, TipDialogFragment.TipListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_detail);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    public void onClick(View v)
    {
        // TODO: SWITCH
    }

    private Task<Void> addTip(final DocumentReference tipRef, final Tip tip)
    {
        // TODO: Implement
        return null;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error)
    {
        //TODO: Implement
    }

    private void onWeaponLoaded(Weapon weapon)
    {
        //TODO: Implement
    }

    public void onBackArrowClicked(View view)
    {
        onBackPressed();
    }

    public void onAddTipClicked(View view)
    {
        //TODO: Implement
    }

    @Override
    public void onTip(Tip tip)
    {
        //TODO: Implement
    }

    private void hideKeyboard()
    {
        View view = getCurrentFocus();
        if (view != null)
        {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}