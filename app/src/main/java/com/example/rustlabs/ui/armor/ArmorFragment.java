package com.example.rustlabs.ui.armor;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rustlabs.MainActivity;
import com.example.rustlabs.R;
import com.example.rustlabs.adapter.ArmorAdapter;
import com.example.rustlabs.adapter.WeaponAdapter;
import com.example.rustlabs.databinding.FragmentArmorBinding;
import com.example.rustlabs.databinding.FragmentWeaponsBinding;
import com.example.rustlabs.ui.weapons.WeaponViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;
import java.util.List;

public class ArmorFragment extends Fragment implements ArmorAdapter.OnArmorSelectedListener, View.OnClickListener
{
    private ArmorViewModel armorViewModel;
    private FragmentArmorBinding binding;

    private static final String TAG = "ArmorFragment";

    private static final String ARMOR_COLLECTION = "armor";

    private MainActivity mParentActivity;

    private FirebaseFirestore mFirestore;

    private RecyclerView mArmorRecycler;
    private TextView mEmptyTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mParentActivity = (MainActivity) getActivity();

        armorViewModel =
                new ViewModelProvider(this).get(ArmorViewModel.class);

        binding = FragmentArmorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mEmptyTextView = binding.textArmor;
        armorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
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

        mArmorRecycler = view.findViewById(R.id.recycler_armor);
        initRecyclerView();
    }

    private void getFirestoreUpdateQuery()
    {
        Log.d(TAG, "getFirestoreUpdateQuery() called");
        mFirestore = ((MainActivity) getActivity()).getFirestore();

        if (mFirestore != null)
        {
            armorViewModel.setQuery(mFirestore.collection(ARMOR_COLLECTION).limit(mParentActivity.getRecyclerViewLimit()));
        }
        else
        {
            Log.e(TAG, "getFirestoreUpdateQuery: No Firestore reference!");
        }
    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView() called");
        if (!armorViewModel.hasQuery())
        {
            Log.w(TAG, "No query, not initializing RecyclerView");
            return;
        }

        if (mArmorRecycler == null)
        {
            Log.w(TAG, "initRecyclerView: No armor recycler view found");
            return;
        }

        armorViewModel.setAdapter(new ArmorAdapter(armorViewModel.getQuery(), this)
        {

            @Override
            protected void onDataChanged()
            {
                Log.d(TAG, "onDataChanged() called");
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0)
                {
                    mArmorRecycler.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
                else
                {
                    mArmorRecycler.setVisibility(View.VISIBLE);
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

        mArmorRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mArmorRecycler.setAdapter(armorViewModel.getAdapter());


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
        if (armorViewModel.getAdapter() != null)
        {
            armorViewModel.getAdapter().startListening();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if (armorViewModel.getAdapter() != null)
        {
            armorViewModel.getAdapter().stopListening();
        }
    }

    @Override
    public void onArmorSelected(DocumentSnapshot armor)
    {
        Log.d(TAG, "onArmorSelected() called with: armor = [" + armor + "]");

        if (mParentActivity != null)
        {
            mParentActivity.onArmorSelected(armor);
        }
    }

    @Override
    public void onClick(View v)
    {
        //TODO: implement
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
    }
}