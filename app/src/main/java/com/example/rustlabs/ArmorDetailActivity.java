package com.example.rustlabs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rustlabs.adapter.TipAdapter;
import com.example.rustlabs.model.Armor;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

public class ArmorDetailActivity extends AppCompatActivity implements View.OnClickListener,
        EventListener<DocumentSnapshot>, TipDialogFragment.TipListener
{

    private static final String TAG = "ArmorDetailActivity";

    public static final String KEY_ARMOR_ID = "key_armor_id";

    // UI Members
    private ImageView mImageView;
    private TextView mNameView;
    private TextView mNumTipsView;

    private TextView mProtectionProjectileView;
    private TextView mProtectionMeleeView;
    private TextView mProtectionColdView;
    private TextView mProtectionRadiationView;


    private TextView mTopLocationView;
    private ViewGroup mEmptyView;
    private RecyclerView mTipRecycler;

    // Fragment Members
    private TipDialogFragment mTipDialog;

    // Firestore Members
    private FirebaseFirestore mFirestore;
    private DocumentReference mArmorRef;
    private ListenerRegistration mArmorRegistration;

    // Internal members
    private TipAdapter mTipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_detail);

        mImageView = findViewById(R.id.weapon_image);
        mNameView = findViewById(R.id.weapon_name);
        mNumTipsView = findViewById(R.id.weapon_num_tips);

        mProtectionProjectileView = findViewById(R.id.protection_projectile_detailed);
        mProtectionMeleeView = findViewById(R.id.protection_melee_detailed);
        mProtectionColdView = findViewById(R.id.protection_cold_detailed);
        mProtectionRadiationView = findViewById(R.id.protection_radiation_detailed);

        mTopLocationView = findViewById(R.id.weapon_top_location_detail);
        mEmptyView = findViewById(R.id.view_empty_tips);
        mTipRecycler = findViewById(R.id.recycler_tips);

        findViewById(R.id.weapon_button_back).setOnClickListener(this);
        findViewById(R.id.show_tip_dialog).setOnClickListener(this);

        // Get armor ID from extras
        String armorId = getIntent().getExtras().getString(KEY_ARMOR_ID);
        if (armorId == null)
        {
            throw new IllegalArgumentException("Must pass extra " + KEY_ARMOR_ID);
        }

        mFirestore = FirebaseFirestore.getInstance();

        mArmorRef = mFirestore.collection("armor").document(armorId);

        // Get tips
        Query tipQuery = mArmorRef.collection("tips").orderBy("timestamp",
                                                               Query.Direction.DESCENDING).limit(50);

        mTipAdapter = new TipAdapter(tipQuery) {
            @Override
            protected void onDataChanged()
            {
                if (getItemCount() == 0)
                {
                    mTipRecycler.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                else
                {
                    mTipRecycler.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        };

        mTipRecycler.setLayoutManager(new LinearLayoutManager(this));
        mTipRecycler.setAdapter(mTipAdapter);

        mTipDialog = new TipDialogFragment();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        mTipAdapter.startListening();
        mArmorRegistration = mArmorRef.addSnapshotListener(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        mTipAdapter.stopListening();

        if (mArmorRegistration != null)
        {
            mArmorRegistration.remove();
            mArmorRegistration = null;
        }
    }

    @Override
    public void onClick(View v)
    {
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

    private Task<Void> addTip(final DocumentReference armorRef, final Tip tip)
    {
        // Create reference for new weapon
        final DocumentReference tipRef = armorRef.collection("tips").document();

        return mFirestore.runTransaction(new Transaction.Function<Void>()
        {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException
            {
                Armor armor = transaction.get(armorRef).toObject(Armor.class);

                // Compute new num tips
                int newNumTips = armor.getNumTips() + 1;

                // set weapon info
                armor.setNumTips(newNumTips);

                // Commit to firestore
                transaction.set(armorRef, armor);
                transaction.set(tipRef, tip);

                return null;
            }
        });
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot snapshot,
                        @Nullable FirebaseFirestoreException error)
    {
        if (error != null)
        {
            Log.w(TAG, "onEvent: error", error);
            return;
        }

        onArmorLoaded(snapshot.toObject(Armor.class));
    }

    private void onArmorLoaded(Armor armor)
    {
        mNameView.setText(armor.getName());
        mNumTipsView.setText(String.valueOf(armor.getNumTips()));

        //TODO: update these views
        mAmmoTypeView.setText(weapon.getAmmoType());
        mDamageView.setText(String.valueOf(weapon.getDamage()));


        mTopLocationView.setText(armor.getTopLocation());

        Glide.with(mImageView.getContext()).load(armor.getPicture()).into(mImageView);
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
        addTip(mArmorRef, tip).addOnSuccessListener(this, new OnSuccessListener<Void>()
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