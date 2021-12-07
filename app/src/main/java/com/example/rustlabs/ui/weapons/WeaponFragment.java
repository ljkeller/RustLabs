package com.example.rustlabs.ui.weapons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rustlabs.MainActivity;
import com.example.rustlabs.R;
import com.example.rustlabs.WeaponDetailActivity;
import com.example.rustlabs.adapter.WeaponAdapter;
import com.example.rustlabs.databinding.FragmentWeaponsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class WeaponFragment extends Fragment implements WeaponAdapter.OnWeaponSelectedListener, View.OnClickListener
{
    private static final String TAG = "WeaponFragment";

    private static final String WEAPON_COLLECTION = "weapons";

    private MainActivity mParentActivity;

    private WeaponViewModel weaponViewModel;
    private FragmentWeaponsBinding binding;

    private FirebaseFirestore mFirestore;

    private RecyclerView mWeaponRecycler;
    private TextView mEmptyTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mParentActivity = (MainActivity) getActivity();

        weaponViewModel =
                new ViewModelProvider(this).get(WeaponViewModel.class);

        binding = FragmentWeaponsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mEmptyTextView = binding.textWeapon;
        weaponViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                mEmptyTextView.setText(s);
            }
        });

        getFirestoreUpdateQuery();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mWeaponRecycler = view.findViewById(R.id.recycler_weapons);
        initRecyclerView();
    }

    private void getFirestoreUpdateQuery()
    {
        Log.d(TAG, "getFirestoreUpdateQuery() called");
        mFirestore = ((MainActivity) getActivity()).getFirestore();

        if (mFirestore != null)
        {
            weaponViewModel.setQuery(mFirestore.collection(WEAPON_COLLECTION).limit(mParentActivity.getRecyclerViewLimit()));
        }
        else
        {
            Log.e(TAG, "getFirestoreUpdateQuery: No Firestore reference!");
        }
    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView() called");
        if (!weaponViewModel.hasQuery())
        {
            Log.w(TAG, "No query, not initializing RecyclerView");
            return;
        }

        if (mWeaponRecycler == null)
        {
            Log.w(TAG, "initRecyclerView: No weapon recycler view found");
            return;
        }

        weaponViewModel.setAdapter(new WeaponAdapter(weaponViewModel.getQuery(), this)
        {

            @Override
            protected void onDataChanged()
            {
                Log.d(TAG, "onDataChanged() called");
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0)
                {
                    mWeaponRecycler.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
                else
                {
                    mWeaponRecycler.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e)
            {
                // Show a snackbar on errors
//                Snackbar.make(findViewById(android.R.id.content),
//                              "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
                Log.e(TAG, "onError: FirebaseFirestore error!", e);
            }
        });

        mWeaponRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mWeaponRecycler.setAdapter(weaponViewModel.getAdapter());


    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if (weaponViewModel.getAdapter() != null)
        {
            weaponViewModel.getAdapter().startListening();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if (weaponViewModel.getAdapter() != null)
        {
            weaponViewModel.getAdapter().stopListening();
        }
    }

    @Override
    public void onWeaponSelected(DocumentSnapshot weapon)
    {
        Log.d(TAG, "onWeaponSelected() called with: weapon = [" + weapon + "]");

        // Pass logic to main activity as there are multiple ways to select weapon
        mParentActivity.onWeaponSelected(weapon);
    }

    @Override
    public void onClick(View v)
    {
        //TODO: implement filter on click + more?
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
    }
}