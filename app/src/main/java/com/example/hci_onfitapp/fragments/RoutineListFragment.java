package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.databinding.FragmentListRoutineBinding;
import com.example.hci_onfitapp.databinding.RoutineCardBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class RoutineListFragment extends Fragment {
    private RoutineViewModel viewModel;
    private FragmentListRoutineBinding binding;
    private RoutineCardBinding routineCardBinding;

    private RecyclerView recyclerView;

    private RoutineAdapter routinesAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListRoutineBinding.inflate(getLayoutInflater());
        routineCardBinding = RoutineCardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        recyclerView = binding.userRecyclerView;
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
