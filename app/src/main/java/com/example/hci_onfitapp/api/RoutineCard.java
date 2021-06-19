package com.example.hci_onfitapp.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.FavouritesModel;
import com.example.hci_onfitapp.databinding.RoutineCardBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

    public class RoutineCard extends Fragment {

        private FavouritesModel favViewModel;
        private RoutineViewModel routinesViewModel;
        private TextView title;
        private TextView author;
        private TextView detail;
        private View view;
        private @NonNull RoutineCardBinding binding;
        private RoutineData routineData;
        private int routineId;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = RoutineCardBinding.inflate(getLayoutInflater());
            title = binding.routineTitle;
            detail = binding.routineDescription;
            view = binding.getRoot();
            return view;
        }

        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            if (getArguments() != null) {
                routineId = getArguments().getInt("routineId");
            }

            routinesViewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);
            routinesViewModel.getRoutineById(routineId);

            routinesViewModel.getCurrentRoutine().observe(getViewLifecycleOwner(), routineData -> {
                if (routineData != null) {
                    this.routineData = routineData;
                    title.setText(routineData.getName());
                    author.setText(routineData.getUser().getUsername());
                    detail.setText(routineData.getDetail());
                }
            });
            favViewModel = new ViewModelProvider(getActivity()).get(FavouritesModel.class);
        }
    }