package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.databinding.FragmentEndRoutineBinding;

import org.jetbrains.annotations.NotNull;


public class EndRoutineFragment extends Fragment {
    private Button volver;
    private int routineId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentEndRoutineBinding binding = FragmentEndRoutineBinding.inflate(getLayoutInflater());
        volver = binding.volver;
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            routineId = getArguments().getInt("routineId");
        }
        volver.setOnClickListener(v -> {
            NavDirections action = EndRoutineFragmentDirections.actionEndRoutineFragmentToViewRoutineFragment().setRoutineId(routineId);
            Navigation.findNavController(view).navigate(action);
        });
    }
}
