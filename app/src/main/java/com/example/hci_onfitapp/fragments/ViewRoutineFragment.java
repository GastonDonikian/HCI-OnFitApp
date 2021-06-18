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
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.databinding.FragmentViewRoutineBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewRoutineFragment extends Fragment {
    private RoutineViewModel viewModel;

    private RoutineAdapter routinesAdapter;

    private FragmentViewRoutineBinding binding;
    private RoutineData routineData;


    private RecyclerView recyclerViewElong;
    private RecyclerView recyclerViewPrin;
    private RecyclerView recyclerViewEntrada;
    private TextView routineTitle;
    private int routineId;
    private Integer routId;


    public ViewRoutineFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentViewRoutineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        routineTitle = binding.routTitle;
        /*
        recyclerViewElong = binding.recyclerViewElong;
        recyclerViewElong.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPrin = binding.recyclerViewPrincipal;
        recyclerViewPrin.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEntrada = binding.recyclerViewEntrada;
        recyclerViewEntrada.setLayoutManager(new LinearLayoutManager(getContext()));

         */

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            routineId = getArguments().getInt("routineId");
        }
        viewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);
        int i=0;
        while(i<10) {
            viewModel.getRoutineById(routineId);
            i++;
        }
        viewModel.getCurrentRoutine().observe(getViewLifecycleOwner(), routineData -> {
                this.routineData = routineData;
                routineTitle.setText(routineData.getName());
        });
    }

}
