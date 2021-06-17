package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci_onfitapp.AppPreferences;
import com.example.hci_onfitapp.HomeActivity;
import com.example.hci_onfitapp.databinding.FragmentSettingsBinding;
import com.example.hci_onfitapp.viewModel.UserViewModel;

public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;
    AppPreferences preferences;
    UserViewModel userviewModel;

    View view;

    private HomeActivity main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userviewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        preferences = new AppPreferences(getActivity().getApplication());
        //binding.logOutButtom.setOnClickListener(v -> logout());
        //switchDarkMode = binding.enableDarkModeSwitch;
       // if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
         //   switchDarkMode.setChecked(true);
       // switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
       //     preferences.setNightModeState(isChecked);
        //    restartApp();
       // });

       // main = (HomeActivity) getActivity();

       // main.showUpButton();
       // main.setNavigationVisibility(false);
        return view;
    }
}


