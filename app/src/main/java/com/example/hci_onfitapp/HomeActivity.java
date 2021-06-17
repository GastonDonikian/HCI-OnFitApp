package com.example.hci_onfitapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.hci_onfitapp.viewModel.UserViewModel;
import com.example.hci_onfitapp.viewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private UserViewModel userviewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userviewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userviewModel.setUserData();

        // No lo mires muy fuerte que se rompe.
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        final NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        userviewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userviewModel.setUserData();

    }
}