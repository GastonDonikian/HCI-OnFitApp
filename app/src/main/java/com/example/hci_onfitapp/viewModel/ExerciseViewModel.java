package com.example.hci_onfitapp.viewModel;

import android.app.Application;
import android.text.format.Time;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.hci_onfitapp.App;
import com.example.hci_onfitapp.api.ApiResponse;
import com.example.hci_onfitapp.api.data.CycleData;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.data.ExerciseData;
import com.example.hci_onfitapp.api.model.ApiRoutine;
import com.example.hci_onfitapp.api.model.ApiRoutineService;
import com.example.hci_onfitapp.api.model.PagedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Response;

public class ExerciseViewModel extends AndroidViewModel {



    private MediatorLiveData<PagedList<CycleExerciseData>> EntradaExercises = new MediatorLiveData<>();
    private MediatorLiveData<PagedList<CycleExerciseData>> PrinExercises = new MediatorLiveData<>();
    private MediatorLiveData<PagedList<CycleExerciseData>> ElongExercises = new MediatorLiveData<>();
    private MutableLiveData<Boolean> isFav = new MutableLiveData<>();

    private ApiRoutineService routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private App app;

    private boolean started = false; //borrar

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


    public MutableLiveData<PagedList<CycleExerciseData>> getEntradaExercises() {
        return EntradaExercises;
    }

    public MutableLiveData<PagedList<CycleExerciseData>> getPrinExercises() {
        return PrinExercises;
    }

    public MutableLiveData<PagedList<CycleExerciseData>> getElongExercises() {return ElongExercises;}


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
}

