package com.example.hci_onfitapp.api.model;


import com.example.hci_onfitapp.api.ApiResponse;
import com.example.hci_onfitapp.api.data.RoutineData;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiRoutineService {
    @GET("routines")
    Single<PagedList<RoutineData>> getRoutines(
            @QueryMap Map<String, String> options
    );

    @GET("users/current/routines/")
    Single<PagedList<RoutineData>> getUserRoutines(
            @QueryMap Map<String, String> options
    );


    @GET("routines/{routineId}")
    Single<RoutineData> getRoutineById(
            @Path("routineId") Integer routineId
    );

//    @GET("routines/{routineId}/cycles")
//    Single<PagedList<RoutineCycleData>> getRoutineCycles(
//            @Path("routineId") Integer routineId,
//            @QueryMap Map<String, String> options
//    );

//    @GET("routines/{routineId}/cycles/{cycleId}/exercises")
//    Single<PagedList<ExerciseData>> getExercises(
//            @Path("routineId") Integer routineId,
//            @Path("cycleId") Integer cycleId,
//            @QueryMap Map<String, String> options
//    );

    @GET("favourites")
    Single<PagedList<RoutineData>> getFavouriteRoutines(
            @QueryMap Map<String, String> options
    );

    @POST("reviews/{routineId}")
    Single<RoutineData> rateRoutine(
            @Path("routineId") Integer routineId,
            @Body RoutineRating rating
    );

    @POST("favourites/{routineId}/")
    Single<Response<Void>> favRoutine(
            @Path("routineId") Integer routineId
    );

//    @POST("executions/{routineId}")
//    Single<RoutineData> addRoutineExecution(
//            @Path("routineId") Integer routineId,
//            @Body RoutineExecution routineExecution
//    );

    @DELETE("favourites/{routineId}/")
    Single<Response<Void>> unfavRoutine(
            @Path("routineId") Integer routineId
    );
}
