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
import com.example.rustlabs.model.Structure;
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

public class StructureDetailActivity extends AppCompatActivity implements View.OnClickListener,
        EventListener<DocumentSnapshot>, TipDialogFragment.TipListener
{

    private static final String TAG = "StructureDetailActivity";

    public static final String KEY_STRUCTURE_ID = "key_structure_id";

    // UI Members
    private ImageView mImageView;
    private TextView mNameView;
    private TextView mNumTipsView;

    private TextView mC4CostView;
    private TextView mRocketCostView;
    private TextView mExplosiveAmmoCostView;
    private TextView mSatchelCostView;
    private TextView mHealthView;

    private ViewGroup mEmptyView;
    private RecyclerView mTipRecycler;

    // Fragment Members
    private TipDialogFragment mTipDialog;

    // Firestore Members
    private FirebaseFirestore mFirestore;
    private DocumentReference mStructureRef;
    private ListenerRegistration mStructureRegistration;

    // Internal members
    private TipAdapter mTipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure_detail);

        mImageView = findViewById(R.id.structure_image);
        mNameView = findViewById(R.id.structure_name);
        mNumTipsView = findViewById(R.id.structure_num_tips);

        mC4CostView = findViewById(R.id.c4_cost);
        mRocketCostView = findViewById(R.id.rocket_cost);
        mExplosiveAmmoCostView = findViewById(R.id.explosive_ammo_cost);
        mSatchelCostView = findViewById(R.id.satchel_cost);
        mHealthView = findViewById(R.id.structure_health);

        mEmptyView = findViewById(R.id.view_empty_tips);
        mTipRecycler = findViewById(R.id.recycler_tips);

        findViewById(R.id.structure_button_back).setOnClickListener(this);
        findViewById(R.id.show_tip_dialog).setOnClickListener(this);

        // Get structure ID from extras
        String structureId = getIntent().getExtras().getString(KEY_STRUCTURE_ID);
        if (structureId == null)
        {
            throw new IllegalArgumentException("Must pass extra " + KEY_STRUCTURE_ID);
        }

        mFirestore = FirebaseFirestore.getInstance();

        mStructureRef = mFirestore.collection("structures").document(structureId);

        // Get tips
        Query tipQuery = mStructureRef.collection("tips").orderBy("timestamp",
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
        mStructureRegistration = mStructureRef.addSnapshotListener(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        mTipAdapter.stopListening();

        if (mStructureRegistration != null)
        {
            mStructureRegistration.remove();
            mStructureRegistration = null;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.structure_button_back:
                onBackArrowClicked(v);
                break;
            case R.id.show_tip_dialog:
                onAddTipClicked(v);
                break;
        }
    }

    private Task<Void> addTip(final DocumentReference structureRef, final Tip tip)
    {
        // Create reference for new weapon
        final DocumentReference tipRef = structureRef.collection("tips").document();

        return mFirestore.runTransaction(new Transaction.Function<Void>()
        {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException
            {
                Structure structure = transaction.get(structureRef).toObject(Structure.class);

                // Compute new num tips
                int newNumTips = structure.getNumTips() + 1;

                // set weapon info
                structure.setNumTips(newNumTips);

                // Commit to firestore
                transaction.set(structureRef, structure);
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

        onStructureLoaded(snapshot.toObject(Structure.class));
    }

    private void onStructureLoaded(Structure structure)
    {
        mNameView.setText(structure.getName());
        mNumTipsView.setText(String.valueOf(structure.getNumTips()));

        mC4CostView.setText(String.valueOf(structure.getRaidC4()));
        mRocketCostView.setText(String.valueOf(structure.getRaidRocket()));
        mExplosiveAmmoCostView.setText(String.valueOf(structure.getRaidExplosiveAmmo()));
        mSatchelCostView.setText(String.valueOf(structure.getRaidSatchel()));
        mHealthView.setText(String.valueOf(structure.getHealth()));

        Glide.with(mImageView.getContext()).load(structure.getPicture()).into(mImageView);
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
        addTip(mStructureRef, tip).addOnSuccessListener(this, new OnSuccessListener<Void>()
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
                Snackbar.make(findViewById(android.R.id.content), "Failed to add tip",
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