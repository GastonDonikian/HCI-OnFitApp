package com.example.hci_onfitapp.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.hci_onfitapp.api.data.CycleData;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.data.ExerciseData;
import com.example.hci_onfitapp.api.model.ApiRoutine;
import com.example.hci_onfitapp.api.model.ApiRoutineService;
import com.example.hci_onfitapp.api.model.PagedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExerciseViewModel extends AndroidViewModel {



    private MutableLiveData<List<CycleExerciseData>> EntradaExercises = new MutableLiveData<>();
    private MutableLiveData<List<CycleExerciseData>> PrinExercises = new MutableLiveData<>();
    private MutableLiveData<List<CycleExerciseData>> ElongExercises = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFav = new MutableLiveData<>();

    private ApiRoutineService routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();

    private boolean started = false; //borrar
    private boolean played; // borrar

    private int currentCycle;
    private String cycleTitle;
    private int currentExercise;
    private boolean isFirstTime = true;
    private boolean finished;
    private int status;
    private ArrayList<ExerciseData> currCycle;
    private boolean executed;


    public ExerciseViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        routinesService = new ApiRoutine(application);
    }

    public void refresh(int routineId) {
        fetchFromRemote(routineId);
    }

    private void fetchFromRemote(int routineId) {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("size", "100");

        List<CycleData> routineCycles = new ArrayList<>();

        disposable.add(
                routinesService.getRoutineCycles(routineId, options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<CycleData>>() {
                            @Override
                            public void onSuccess(@NonNull PagedList<CycleData> cycles) {
                                routineCycles.addAll(cycles.getContent());
                                for (CycleData cycle : routineCycles) {
                                    disposable.add(
                                            routinesService.getExercises(cycle.getId(), options)
                                                    .subscribeOn(Schedulers.newThread())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeWith(new DisposableSingleObserver<PagedList<CycleExerciseData>>() {
                                                        @Override
                                                        public void onSuccess(@NonNull PagedList<CycleExerciseData> cycleExercises) {
                                                            switch (cycle.getType()) {
                                                                case "warmup":
                                                                    EntradaExercises.setValue(cycleExercises.getContent());
                                                                    break;
                                                                case "exercise":
                                                                    PrinExercises.setValue(cycleExercises.getContent());
                                                                    break;
                                                                case "cooldown":
                                                                    ElongExercises.setValue(cycleExercises.getContent());
                                                                    break;
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                                            e.printStackTrace();
                                                        }
                                                    })
                                    );
                                }
                            }
                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );

    }



    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public boolean getExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }


    public MutableLiveData<List<CycleExerciseData>> getEntradaExercises() {
        return EntradaExercises;
    }

    public MutableLiveData<List<CycleExerciseData>> getPrinExercises() {
        return PrinExercises;
    }

    public MutableLiveData<List<CycleExerciseData>> getElongExercises() {return ElongExercises;}


    public void setStarted(boolean state) {
        started = state;
    }

    public boolean getStarted() {
        return started;
    }

    public boolean getIsFirstTime(){
        return isFirstTime;
    }
    public void setIsFirstTime(boolean state){
        isFirstTime = state;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public int getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public void setCurrentExercise(int currentExercise) {
        this.currentExercise = currentExercise;
    }

    public boolean getPlayed() {
        return played;
    }
}

