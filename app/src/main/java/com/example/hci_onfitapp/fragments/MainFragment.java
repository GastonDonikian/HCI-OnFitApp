package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.hci_onfitapp.databinding.FragmentMainBinding;
import com.example.hci_onfitapp.fragments.MainFragmentDirections;

import com.example.hci_onfitapp.R;

public class MainFragment extends Fragment {
    private String arg1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle a = getArguments();
        if(a!=null){
            arg1 = a.getString("RoutineId");
        }else {
            arg1=null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentMainBinding binding = FragmentMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();

        Button loginBtn = rootView.findViewById(R.id.to_log_in);
        Button registerBtn = rootView.findViewById(R.id.to_register);

        loginBtn.setOnClickListener((v) -> {

            NavController navController = Navigation.findNavController(v);
            @NonNull NavDirections action = MainFragmentDirections.actionMainFragmentToLogin();
            //action.setRoutineId(arg1);
            navController.navigate(action);

        });
        registerBtn.setOnClickListener(v -> {

            NavController navController = Navigation.findNavController(v);
            @NonNull NavDirections action = MainFragmentDirections.actionMainFragmentToRegisterFragment();
            //action.setRoutineId(arg1);
            navController.navigate(action);

        });

        return rootView;
    }
}
