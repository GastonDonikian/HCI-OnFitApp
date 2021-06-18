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

//TODO NO SE USA PERO LA DEJO POR LAS DUDAS. FUE EN EL INTENTO DE LOS DATOS

    public class RoutineCard extends Fragment {

        //private ExercisesViewModel exercisesViewModel;
        private FavouritesModel favViewModel;
        private RoutineViewModel routinesViewModel;

        //private ExercisesAdapter warmUpAdapter = new ExercisesAdapter(new ArrayList<>());
        //private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
        //private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());

        private RecyclerView recyclerViewWarmUp;
        private RecyclerView recyclerViewMain;
        private RecyclerView recyclerViewCooldown;
        private FloatingActionButton playBtn;

        private TextView title;
        private TextView author;
        private TextView detail;
        private TextView id;

        private MenuItem fav;
        private MenuItem unfav;

        private View view;

        private ImageView image;

        private @NonNull RoutineCardBinding binding;

        private RoutineData routineData;

        private int routineId;

        //private MainActivity main;

        //PlayModeDialog playModeDialog;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = RoutineCardBinding.inflate(getLayoutInflater());
            //ViewRoutineFragment viewRoutineFragment = new ViewRoutineFragment();
            //viewRoutineFragment.setArguments(new Bundle().putInt("routineId", binding.routineId.getText().cha));

           // recyclerViewWarmUp = binding.warmUpExercises;
           // recyclerViewMain = binding.mainExercises;
           // recyclerViewCooldown = binding.cooldownExercises;

            title = binding.routineTitle;
            detail = binding.routineDescription;

            //playBtn = binding.playBtn;
            

            view = binding.getRoot();

            //main = (MainActivity) getActivity();

            //main.showUpButton();
            //main.setNavigationVisibility(false);

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
                    //playModeDialog = new PlayModeDialog(routineData, getView());
                }
            });

            //playBtn.setOnClickListener(v -> openPlayModeDialog());

            favViewModel = new ViewModelProvider(getActivity()).get(FavouritesModel.class);

            //exercisesViewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);
            //exercisesViewModel.refresh(routineId);

            //recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getContext()));
            //recyclerViewWarmUp.setAdapter(warmUpAdapter);

            //recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
            //recyclerViewMain.setAdapter(mainAdapter);

            //recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
            //recyclerViewCooldown.setAdapter(cooldownAdapter);

            //observeExerciseViewModel();
        }
/*
        private void observeExerciseViewModel() {
            exercisesViewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
                if (warmupExercises != null) {
                    warmUpAdapter.updateExercises(warmupExercises);
                }
            });

            exercisesViewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
                if (mainExercises != null) {
                    mainAdapter.updateExercises(mainExercises);
                }
            });

            exercisesViewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
                if (cooldownExercises != null) {
                    cooldownAdapter.updateExercises(cooldownExercises);
                }
            });

        }
        */

        private void share() {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, routineData.getName());
            sharingIntent.putExtra("RoutineId", routineId);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.name) + ": http://www.fitnesshub.com/Routines/" + routineId);
            startActivity(Intent.createChooser(sharingIntent, "Share Rutine"));
        }


        public void favRoutine() {
            fav.setVisible(true);
            unfav.setVisible(false);
        }

        public void unfavRoutine() {
            fav.setVisible(false);
            unfav.setVisible(true);
        }



    }