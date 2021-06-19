package com.example.hci_onfitapp.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.hci_onfitapp.api.data.CycleData;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.PagedList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public T getData(){
        return data;
    }

    public ApiError getError() {
        return error;
    }

    public ApiResponse(Response<T> response){
        parseResponse(response);
    }

    public ApiResponse(Throwable t){
        this.error = buildError(t.getMessage());
    }

    private void parseResponse(Response<T> response){
        if(response.isSuccessful()) {
            this.data = response.body();
            return;
        }

        if(response.errorBody() == null){
            this.error = buildError("Missing error body");
            return;
        }
        String message;
        try {
            message = response.errorBody().string();
        }catch (IOException exception){
            Log.e("API", exception.toString());
            this.error = buildError(exception.getMessage());
            return;
        }

        if(message !=null && message.trim().length() > 0){
            Gson gson = new Gson();
            this.error = gson.fromJson(message, new TypeToken<ApiError>(){}.getType());
        }
    }

    private ApiError buildError(String message){
        ApiError error = new ApiError(ApiError.UNEXPECTED_ERROR,"Unexpected error" );
        if(message!= null){
            List<String> details = new ArrayList<>();
            details.add(message);
            error.setDetails(details);
        }
        return error;
    }

    public static interface ApiRoutineServiceInner {

        @GET("routines/{routineId}")
        LiveData<ApiResponse<RoutineData>> getRoutine(@Path("routineId") int routineId);

        //cycles
        @GET("routines/{routineId}/cycles")
        LiveData<ApiResponse<PagedList<CycleData>>> getRoutineCycles(@Path("routineId") int routineId);

        @GET("routines/{routineId}/cycles/{cycleId}")
        LiveData<ApiResponse<CycleData>> getCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

        @GET("cycles/{cycleId}/exercises")
        LiveData<ApiResponse<PagedList<CycleExerciseData>>> getCycle(@Path("cycleId") int cycleId);

        @GET("cycles/{cycleId}/exercises")
        LiveData<ApiResponse<PagedList<CycleExerciseData>>> getCycleExercises(@Path("cycleId") int cycleId);

    }
}
