package com.example.rustlabs.ui.structures;

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
import com.example.rustlabs.adapter.ArmorAdapter;
import com.example.rustlabs.adapter.StructureAdapter;
import com.example.rustlabs.databinding.FragmentArmorBinding;
import com.example.rustlabs.databinding.FragmentStructuresBinding;
import com.example.rustlabs.ui.armor.ArmorViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class StructuresFragment extends Fragment implements StructureAdapter.OnStructureSelectedListener, View.OnClickListener
{

    private StructuresViewModel structuresViewModel;
    private FragmentStructuresBinding binding;

    private static final String TAG = "StructureFragment";

    private static final String STRUCTURE_COLLECTION = "structures";

    private MainActivity mParentActivity;

    private FirebaseFirestore mFirestore;

    private RecyclerView mStructureRecycler;
    private TextView mEmptyTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mParentActivity = (MainActivity) getActivity();

        structuresViewModel =
                new ViewModelProvider(this).get(StructuresViewModel.class);

        binding = FragmentStructuresBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mEmptyTextView  = binding.textStructures;
        structuresViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
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

        mStructureRecycler = view.findViewById(R.id.recycler_structures);
        initRecyclerView();
    }

    private void getFirestoreUpdateQuery()
    {
        Log.d(TAG, "getFirestoreUpdateQuery() called");
        mFirestore = ((MainActivity) getActivity()).getFirestore();

        if (mFirestore != null)
        {
            structuresViewModel.setQuery(mFirestore.collection(STRUCTURE_COLLECTION).limit(mParentActivity.getRecyclerViewLimit()));
        }
        else
        {
            Log.e(TAG, "getFirestoreUpdateQuery: No Firestore reference!");
        }
    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView() called");
        if (!structuresViewModel.hasQuery())
        {
            Log.w(TAG, "No query, not initializing RecyclerView");
            return;
        }

        if (mStructureRecycler == null)
        {
            Log.w(TAG, "initRecyclerView: No structure recycler view found");
            return;
        }

        structuresViewModel.setAdapter(new StructureAdapter(structuresViewModel.getQuery(), this)
        {

            @Override
            protected void onDataChanged()
            {
                Log.d(TAG, "onDataChanged() called");
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0)
                {
                    mStructureRecycler.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
                else
                {
                    mStructureRecycler.setVisibility(View.VISIBLE);
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

        mStructureRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mStructureRecycler.setAdapter(structuresViewModel.getAdapter());


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
        if (structuresViewModel.getAdapter() != null)
        {
            structuresViewModel.getAdapter().startListening();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if (structuresViewModel.getAdapter() != null)
        {
            structuresViewModel.getAdapter().stopListening();
        }
    }

    @Override
    public void onStructureSelected(DocumentSnapshot structure)
    {
        Log.d(TAG, "onStructureSelected() called with: armor = [" + structure + "]");

        if (mParentActivity != null)
        {
            mParentActivity.onStructureSelected(structure);
        }
    }

    @Override
    public void onClick(View v)
    {
        //TODO: implement
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
    }
}