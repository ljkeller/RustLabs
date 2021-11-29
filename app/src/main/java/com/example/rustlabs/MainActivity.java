package com.example.rustlabs;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rustlabs.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rustlabs.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private MainActivityViewModel mViewModel;

    private int RC_SIGN_IN = 1822;

//    private void onSignInResult(FirebaseAuthUIAuthenticationResult result)
//    {
//        IdpResponse response = result.getIdpResponse();
//        if (result.getResultCode() == RESULT_OK) {
//            // Successfully signed in
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT);
//            // ...
//        } else {
//            Toast.makeText(this, "Sign in un-successful", Toast.LENGTH_SHORT);
//            // Sign in failed. If response is null the user canceled the
//            // sign-in flow using the back button. Otherwise check
//            // response.getError().getErrorCode() and handle the error.
//            // ...
//        }
//    }

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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onStart()
    {
        super.onStart();

//        // Choose authentication providers
//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build());
//
//        // Create and launch sign-in intent
//        Intent signInIntent = AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .build();
//        signInLauncher.launch(signInIntent);
//
//
//        // Start sign in if necessary
//        if (shouldStartSignIn())
//        {
//            startSignIn();
//            return;
//        }

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

//    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
//            new FirebaseAuthUIActivityResultContract(),
//            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
//                @Override
//                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
//                    onSignInResult(result);
//                }
//            }
//    );

    private boolean shouldStartSignIn()
    {
        return (!mViewModel.getIsSigningIn() && mAuth.getCurrentUser() == null);
    }

    public void startSignIn(View view)
    {
//        // Sign in with FirebaseUI
//        Intent intent = FirebaseUtil.getAuthUI()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(Collections.singletonList(
//                        new AuthUI.IdpConfig.EmailBuilder().build()))
//                .setIsSmartLockEnabled(false)
//                .build();
//
//        startActivityForResult(intent, RC_SIGN_IN);
        mViewModel.setIsSigningIn(true);
    }

    private void onFilter() {
        //TODO: Implement
    }

    private void showTodoToast()
    {
        Toast.makeText(this, "TODO: Implement", Toast.LENGTH_SHORT).show();
    }
}