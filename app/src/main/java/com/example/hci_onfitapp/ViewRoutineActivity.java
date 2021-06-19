package com.example.hci_onfitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hci_onfitapp.api.ExerciseAdapter;
import com.example.hci_onfitapp.api.data.CycleData;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.ApiRoutine;
import com.example.hci_onfitapp.api.model.ApiRoutineService;
import com.example.hci_onfitapp.api.model.Status;
import com.example.hci_onfitapp.databinding.FragmentViewRoutineBinding;
import com.example.hci_onfitapp.viewModel.ExerciseViewModel;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ViewRoutineActivity extends AppCompatActivity {

    String routineId;

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

    private boolean fav = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_routine);

        System.out.println("buenas "+ routineId);

        Intent intent = getIntent();
        routineId = intent.getStringExtra("routineID");

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

        routinesService = new ApiRoutine(this);

        viewModel = new ViewModelProvider(this).get(RoutineViewModel.class);

        viewModel.getRoutineById(new Integer(routineId));

        viewModel.getCurrentRoutine().observe(this, routineData -> {
            this.routineData = routineData;
            routineTitle.setText(routineData.getName());
            ratingBar.setRating(routineData.getAverageRating());
            viewModel.getFavouriteRoutines();
            viewModel.getUserFavouriteRoutines().observe(this, favourites -> {
                boolean isFav = false;
                for (RoutineData routine : favourites) {
                    if (routine.getId() == new Integer(routineId)) {
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
                    viewModel.rateRoutine(new Integer(routineId),(int)ratingBar.getRating());

                }
            });
            shareBtn.setOnClickListener(v->{
                share();
            });

        });
//
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

//        fetchFromRemote(new Integer(routineId));
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
        exerciseViewModel.getEntradaExercises().observe(this, EntradaExercises -> {
            if (EntradaExercises != null) {
                EntradaAdapter.updateExercises(EntradaExercises.getContent());
            }
        });

        exerciseViewModel.getPrinExercises().observe(this, PrinExercises -> {
            if (PrinExercises != null) {
                PrinAdapter.updateExercises(PrinExercises.getContent());
            }
        });

        exerciseViewModel.getElongExercises().observe(this, ElongExercises -> {
            if (ElongExercises != null) {
                ElongAdapter.updateExercises(ElongExercises.getContent());
            }
        });
    }
//
//    private void fetchFromRemote(int routineId) {
//        Map<String, String> options = new HashMap<>();
//        options.put("page", "0");
//        options.put("size", "100");
//
//        List<CycleData> routineCyclesList = new ArrayList<>();
//        app.getRoutineRepository().getRoutineCycles(routineId).observe(this, routineCycles -> {
//            if (routineCycles.getStatus() == Status.SUCCESS) {
//                routineCyclesList.addAll(routineCycles.getData().getContent());
//                int i = 0;
//                List<CycleData> cycleDataList = new ArrayList<>();
//                for (CycleData ciclo : routineCyclesList) {
//                    app.getRoutineRepository().getCycleExercise(ciclo.getId()).observe(this, exercise -> {
//                        if (exercise.getStatus() == Status.SUCCESS) {
//                            ciclo.setCycleExercises(exercise.getData());
//                            String type = ciclo.getType();
//                            switch (type) {
//                                case "warmup":
//                                    System.out.println(ciclo.getCycleExercises());
//                                    repesEntrada.setText('x' + String.valueOf(ciclo.getRepetitions()));
//                                    EntradaAdapter = new ExerciseAdapter(ciclo.getCycleExercises());
//                                    recyclerViewEntrada = binding.recyclerViewEntrada;
//                                    recyclerViewEntrada.setLayoutManager(new LinearLayoutManager(this));
//                                    recyclerViewEntrada.setAdapter(EntradaAdapter);
//                                    break;
//                                case "exercise":
//                                    PrinAdapter = new ExerciseAdapter(ciclo.getCycleExercises());
//                                    repesPrin.setText('x' + String.valueOf(ciclo.getRepetitions()));
//                                    recyclerViewPrin = binding.recyclerViewPrincipal;
//                                    recyclerViewPrin.setLayoutManager(new LinearLayoutManager(this));
//                                    recyclerViewPrin.setAdapter(PrinAdapter);
//                                    break;
//                                case "cooldown":
//                                    ElongAdapter = new ExerciseAdapter(ciclo.getCycleExercises());
//                                    repesElong.setText('x' + String.valueOf(ciclo.getRepetitions()));
//                                    recyclerViewElong = binding.recyclerViewElong;
//                                    recyclerViewElong.setLayoutManager(new LinearLayoutManager(this));
//                                    recyclerViewElong.setAdapter(ElongAdapter);
//                                    break;
//                            }
//                            cycleDataList.add(ciclo);
//                        }
//                    });
//                }
//            }
//        });

    private void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.onfit.com/Routines/" + routineId);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Mensaje");
        startActivity(shareIntent);
    }
}