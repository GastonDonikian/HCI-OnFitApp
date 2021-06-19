package com.example.hci_onfitapp.fragments;

import android.content.Intent;
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

import com.example.hci_onfitapp.App;
import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.ExerciseAdapter;
import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.api.data.CycleData;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.ApiRoutine;
import com.example.hci_onfitapp.api.model.ApiRoutineService;
import com.example.hci_onfitapp.api.model.FavouritesModel;
import com.example.hci_onfitapp.api.model.PagedList;
import com.example.hci_onfitapp.api.model.Status;
import com.example.hci_onfitapp.databinding.FragmentViewRoutineBinding;
import com.example.hci_onfitapp.viewModel.ExerciseViewModel;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewRoutineFragment extends Fragment {
    private RoutineViewModel viewModel;
    private ExerciseViewModel exerciseViewModel;
    private RatingBar ratingBar;

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
    private TextView repesElong;
    private TextView repesEntrada;
    private TextView repesPrin;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiRoutineService routinesService;
    private App app;
    View view;
    private FloatingActionButton favouriteBtn;
    private FloatingActionButton shareBtn;

    private @NonNull
    int routineId;
    private boolean fav = false;


    public ViewRoutineFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentViewRoutineBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        routineTitle = binding.routTitle;
        ratingBar = binding.ratingBar;
        favButton = binding.floatingActionButtonFavorite;
        shareBtn = binding.floatingActionButtonShare;
        repesElong = binding.repesElong;
        repesEntrada = binding.repesEntrada;
        repesPrin = binding.repesPrin;

        favouriteBtn = view.findViewById(R.id.floatingActionButtonFavorite);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routinesService = new ApiRoutine(getActivity());
        app = (App) requireActivity().getApplication();
        if (getArguments() != null) {
            routineId = getArguments().getInt("routineId");
        }
        viewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);


        viewModel.getRoutineById(routineId);
        viewModel.getCurrentRoutine().observe(getViewLifecycleOwner(), routineData -> {
            this.routineData = routineData;
            routineTitle.setText(routineData.getName());
            ratingBar.setRating(routineData.getAverageRating());
            viewModel.getFavouriteRoutines();
            viewModel.getUserFavouriteRoutines().observe(getViewLifecycleOwner(), favourites -> {
            boolean isFav = false;
            for (RoutineData routine : favourites) {
                if (routine.getId() == routineId) {
                    isFav = true;
                    break;
                }
            }
            if (isFav) {
                routineData.setFav(true);
            } else {
                routineData.setFav(false);
            }
            });
            favouriteBtn.setOnClickListener(v ->{
                if(!routineData.isFav()){
                    addFav(routineData.getId());
                }
                else{
                    unFav(routineData.getId());
                }});

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                {
                    viewModel.rateRoutine(routineId,(int)ratingBar.getRating());

                }
            });
            shareBtn.setOnClickListener(v->{
                share();
            });

        });

        exerciseViewModel = new ViewModelProvider(getActivity()).get(ExerciseViewModel.class);
        fetchFromRemote(routineId);
        observeExerciseViewModel();
    }

    private void addFav(int routId){
        viewModel.addFav(routId);
        routineData.setFav(true);
    }

    private void unFav(int routId){
        viewModel.unFav(routId);
        routineData.setFav(false);
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

    private void fetchFromRemote(int routineId) {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("size", "100");

        List<CycleData> routineCyclesList = new ArrayList<>();
        app.getRoutineRepository().getRoutineCycles(routineId).observe(requireActivity(), routineCycles -> {
            if (routineCycles.getStatus() == Status.SUCCESS) {
                routineCyclesList.addAll(routineCycles.getData().getContent());
                int i = 0;
                List<CycleData> cycleDataList = new ArrayList<>();
                for (CycleData ciclo : routineCyclesList) {
                    app.getRoutineRepository().getCycleExercise(ciclo.getId()).observe(requireActivity(), exercise -> {
                        if (exercise.getStatus() == Status.SUCCESS) {
                            ciclo.setCycleExercises(exercise.getData());
                            String type = ciclo.getType();
                            switch (type) {
                                case "warmup":
                                    System.out.println(ciclo.getCycleExercises());
                                    repesEntrada.setText('x' + String.valueOf(ciclo.getRepetitions()));
                                    EntradaAdapter = new ExerciseAdapter(ciclo.getCycleExercises());
                                    recyclerViewEntrada = binding.recyclerViewEntrada;
                                    recyclerViewEntrada.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerViewEntrada.setAdapter(EntradaAdapter);
                                    break;
                                case "exercise":
                                    PrinAdapter = new ExerciseAdapter(ciclo.getCycleExercises());
                                    repesPrin.setText('x' + String.valueOf(ciclo.getRepetitions()));
                                    recyclerViewPrin = binding.recyclerViewPrincipal;
                                    recyclerViewPrin.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerViewPrin.setAdapter(PrinAdapter);
                                    break;
                                case "cooldown":
                                    ElongAdapter = new ExerciseAdapter(ciclo.getCycleExercises());
                                    repesElong.setText('x' + String.valueOf(ciclo.getRepetitions()));
                                    recyclerViewElong = binding.recyclerViewElong;
                                    recyclerViewElong.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerViewElong.setAdapter(ElongAdapter);
                                    break;
                            }
                            cycleDataList.add(ciclo);
                        }
                    });
                }
            }
        });
    }
    private void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.onFit.com/Routines/" + routineId);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Mensaje");
        startActivity(shareIntent);
    }

}
