package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.databinding.FragmentViewRoutineBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

public class ViewRoutineFragment extends Fragment {
    private RoutineViewModel viewModel;

    private RoutineAdapter routinesAdapter;

    private FragmentViewRoutineBinding binding;


    private RecyclerView recyclerViewElong;
    private RecyclerView recyclerViewPrin;
    private RecyclerView recyclerViewEntrada;
    private TextView routTitle;


    public ViewRoutineFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentViewRoutineBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        routTitle = binding.routTitle;
        System.out.println(binding.routTitle.getText().toString());
        recyclerViewElong = binding.recyclerViewElong;
        recyclerViewElong.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPrin = binding.recyclerViewPrincipal;
        recyclerViewPrin.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEntrada = binding.recyclerViewEntrada;
        recyclerViewEntrada.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);
        TextView nombre = view.findViewById(R.id.routTitle);
        nombre.setText(viewModel.getCurrentRoutine().getValue().getName());
    }
}
