package com.example.hci_onfitapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.hci_onfitapp.fragments.ViewRoutineFragment;
import com.example.hci_onfitapp.viewModel.UserViewModel;
import com.example.hci_onfitapp.viewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private UserViewModel userviewModel;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String routineID = intent.getStringExtra("routineID");
        if (routineID != null) {
            Intent toViewRoutineActivity = new Intent(HomeActivity.this, ViewRoutineActivity.class);
            toViewRoutineActivity.putExtra("routineID",routineID);
            startActivity(toViewRoutineActivity);
        }

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

//    public void setUpBottomNavigation() {
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.homeFragment);
//        assert navHostFragment != null;
//        NavigationUI.setupWithNavController(bottomNavigationView,
//                navHostFragment.getNavController());
//    }
//
//    public void setNavigationVisibility(boolean b) {
//        if (b) {
//            bottomNavigationView.setVisibility(View.VISIBLE);
//        } else {
//            bottomNavigationView.setVisibility(View.GONE);
//        }
//    }
}