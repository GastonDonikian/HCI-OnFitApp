package com.example.hci_onfitapp.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.hci_onfitapp.App;
import com.example.hci_onfitapp.api.data.CycleData;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.ApiClient;
import com.example.hci_onfitapp.api.model.BoundResource;
import com.example.hci_onfitapp.api.model.PagedList;
import com.example.hci_onfitapp.api.model.Resource;

public class RoutineRepository {
    private final ApiResponse.ApiRoutineServiceInner apiService;

    public RoutineRepository(App app) {
        this.apiService = ApiClient.create(app, ApiResponse.ApiRoutineServiceInner.class);
    }

    public LiveData<Resource<RoutineData>> getRoutine(int routineId) {
        return new BoundResource<RoutineData, RoutineData>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineData>> createCall() {
                return apiService.getRoutine(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<CycleData>>> getRoutineCycles(int routineId) {
        return new BoundResource<PagedList<CycleData>, PagedList<CycleData>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<CycleData>>> createCall() {
                return apiService.getRoutineCycles(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<CycleExerciseData>>> getCycleExercise(int cycleID) {
        return new BoundResource<PagedList<CycleExerciseData>, PagedList<CycleExerciseData>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<CycleExerciseData>>> createCall() {
                return apiService.getCycleExercises(cycleID);
            }
        }.asLiveData();
    }
}
