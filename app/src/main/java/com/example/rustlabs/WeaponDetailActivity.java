package com.example.rustlabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rustlabs.adapter.TipAdapter;
import com.example.rustlabs.model.Tip;
import com.example.rustlabs.model.Weapon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import org.w3c.dom.Document;

public class WeaponDetailActivity extends AppCompatActivity implements View.OnClickListener,
        EventListener<DocumentSnapshot>, TipDialogFragment.TipListener
{

    private static final String TAG = "WeaponDetailActivity";

    public static final String KEY_WEAPON_ID = "key_weapon_id";

    // UI Members
    private ImageView mImageView;
    private TextView mNameView;
    private TextView mNumTipsView;
    private TextView mAmmoTypeView;
    private TextView mDamageView;
    private TextView mTopLocationView;
    private ViewGroup mEmptyView;
    private RecyclerView mTipRecycler;

    // Fragment Members
    private TipDialogFragment mTipDialog;

    // Firestore Members
    private FirebaseFirestore mFirestore;
    private DocumentReference mWeaponRef;
    private ListenerRegistration mWeaponRegistration;

    // Internal members
    private TipAdapter mTipAdapter;

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

        mTipAdapter.startListening();
        mWeaponRegistration = mWeaponRef.addSnapshotListener(this)
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        mTipAdapter.stopListening();

        if (mWeaponRegistration != null)
        {
            mWeaponRegistration.remove();
            mWeaponRegistration = null;
        }
    }

    @Override
    public void onClick(View v)
    {
        // TODO: SWITCH
        switch (v.getId())
        {
            case R.id.weapon_button_back:
                onBackArrowClicked(v);
                break;
            case R.id.show_tip_dialog:
                onAddTipClicked(v);
                break;
        }
    }

    private Task<Void> addTip(final DocumentReference tipRef, final Tip tip)
    {
        // TODO: Implement
        return null;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot snapshot,
                        @Nullable FirebaseFirestoreException error)
    {
        //TODO: Implement
        if (error != null)
        {
            Log.w(TAG, "onEvent: error", error);
            return;
        }

        onWeaponLoaded(snapshot.toObject(Weapon.class));
    }

    private void onWeaponLoaded(Weapon weapon)
    {
        mNameView.setText(weapon.getName());
        mNumTipsView.setText(String.valueOf(weapon.getNumTips()));
        mAmmoTypeView.setText(weapon.getAmmoType());
        mDamageView.setText(String.valueOf(weapon.getDamage()));
        mTopLocationView.setText(weapon.getTopLocation());

        Glide.with(mImageView.getContext()).load(weapon.getPicture()).into(mImageView);
    }

    public void onBackArrowClicked(View view)
    {
        onBackPressed();
    }

    public void onAddTipClicked(View view)
    {
        mTipDialog.show(getSupportFragmentManager(), TipDialogFragment.TAG);
    }

    @Override
    public void onTip(Tip tip)
    {
        // Must add rating and update count
        addTip(mWeaponRef, tip).addOnSuccessListener(this, new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void unused)
            {
                Log.d(TAG, "onSuccess: Tip Added");

                // Hide keyboard and scroll top
                hideKeyboard();
                mTipRecycler.smoothScrollToPosition(0);
            }
        }).addOnFailureListener(this, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.w(TAG, "onFailure: Add Tip failed", e);

                // Show failure message & scroll top
                hideKeyboard();
                Snackbar.make(findViewById(android.R.id.content), "Failed to add raiting",
                              Snackbar.LENGTH_SHORT).show();
            }
        });
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