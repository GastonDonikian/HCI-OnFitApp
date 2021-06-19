package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.ExerciseAdapter;
import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.FavouritesModel;
import com.example.hci_onfitapp.api.model.PagedList;
import com.example.hci_onfitapp.databinding.FragmentViewRoutineBinding;
import com.example.hci_onfitapp.viewModel.ExerciseViewModel;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class ViewRoutineFragment extends Fragment {
    private RoutineViewModel viewModel;
    private ExerciseViewModel exerciseViewModel;
    private RatingBar ratingBar;
    FavouritesModel favouritesModel;

    private RoutineAdapter routinesAdapter;
    private FavouritesModel favViewModel;

    private FragmentViewRoutineBinding binding;
    private RoutineData routineData;
    private FloatingActionButton favButton;


    private RecyclerView recyclerViewElong;
    private RecyclerView recyclerViewPrin;
    private RecyclerView recyclerViewEntrada;

    private ExerciseAdapter EntradaAdapter;
    private ExerciseAdapter PrinAdapter;
    private ExerciseAdapter ElongAdapter;
    private TextView routineTitle;

    private @NonNull
    int routineId;
    boolean isFav;
    private Integer routId;
    private boolean fav = false;


    public ViewRoutineFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentViewRoutineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        routineTitle = binding.routTitle;
        ratingBar = binding.ratingBar;
        favButton = binding.floatingActionButtonFavorite;

        Button favouriteBtn = view.findViewById(R.id.floatingActionButtonFavorite);
        favouriteBtn.setOnClickListener(v -> addFav(routId));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            routineId = getArguments().getInt("routineId");
            isFav = getArguments().getBoolean("isFav");
            System.out.println(routineId);
            System.out.println("VIEW ROUTINE FRAGMENT");
        }
        viewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);
        favViewModel = new ViewModelProvider(getActivity()).get(FavouritesModel.class);

        viewModel.getRoutineById(routineId);
        viewModel.getCurrentRoutine().observe(getViewLifecycleOwner(), routineData -> {
                this.routineData = routineData;
                routineTitle.setText(routineData.getName());
                ratingBar.setRating(routineData.getAverageRating());
        });

        exerciseViewModel = new ViewModelProvider(getActivity()).get(ExerciseViewModel.class);
        exerciseViewModel.refresh(routineId);
        exerciseViewModel.refresh(routineId);

        System.out.println(exerciseViewModel.getElongExercises().getValue());
        System.out.println(exerciseViewModel.getPrinExercises().getValue());
        System.out.println(exerciseViewModel.getEntradaExercises().getValue());

        ElongAdapter = new ExerciseAdapter(exerciseViewModel.getElongExercises().getValue());
        PrinAdapter = new ExerciseAdapter(exerciseViewModel.getPrinExercises().getValue());
        EntradaAdapter = new ExerciseAdapter(exerciseViewModel.getEntradaExercises().getValue());

        recyclerViewEntrada = binding.recyclerViewEntrada;
        recyclerViewEntrada.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEntrada.setAdapter(EntradaAdapter);

        recyclerViewPrin = binding.recyclerViewPrincipal;
        recyclerViewPrin.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPrin.setAdapter(PrinAdapter);

        recyclerViewElong = binding.recyclerViewElong;
        recyclerViewElong.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewElong.setAdapter(ElongAdapter);
//        observeExerciseViewModel();
    }

    private void addFav(int routId){
        viewModel.addFav(routId);
    }

    private void observeExerciseViewModel() {
        exerciseViewModel.getEntradaExercises().observe(getViewLifecycleOwner(), EntradaExercises -> {
            if (EntradaExercises != null) {
                EntradaAdapter.updateExercises(EntradaExercises.getContent());
            }
        });

        exerciseViewModel.getPrinExercises().observe(getViewLifecycleOwner(), PrinExercises -> {
            if (PrinExercises != null) {
                PrinAdapter.updateExercises(PrinExercises.getContent());
            }
        });

        exerciseViewModel.getElongExercises().observe(getViewLifecycleOwner(), ElongExercises -> {
            if (ElongExercises != null) {
                ElongAdapter.updateExercises(ElongExercises.getContent());
            }
        });
    }

}
