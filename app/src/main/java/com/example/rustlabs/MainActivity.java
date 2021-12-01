package com.example.rustlabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rustlabs.databinding.ActivityMainBinding;
import com.example.rustlabs.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    NavController mNavController;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mViewModel = new MainActivityViewModel();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_weapon, R.id.navigation_structures)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, mNavController);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Start sign in if necessary
        if (shouldStartSignIn())
        {
            startSignIn();
            return;
        }

        //        // Apply filters
        //        onFilter(mViewModel.getFilters());
        //
        //        // Start listening for Firestore updates
        //        if (mAdapter != null)
        //        {
        //            mAdapter.startListening();
        //        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        //        if (mAdapter != null)
        //        {
        //            mAdapter.stopListening();
        //        }
    }


    private boolean shouldStartSignIn()
    {
        Log.d(TAG, "shouldStartSignIn() called");
        return (mAuth.getCurrentUser() == null);
    }

    public void startSignIn()
    {
        Log.d(TAG, "startSignIn() called");

        startActivity(new Intent(this, FirebaseAuthActivity.class));
        finish();
    }

    private void onFilter()
    {
        //TODO: Implement
    }

    private void showTodoToast()
    {
        Toast.makeText(this, "TODO: Implement", Toast.LENGTH_SHORT).show();
    }
}