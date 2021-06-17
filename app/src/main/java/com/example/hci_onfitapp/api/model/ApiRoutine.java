package com.example.hci_onfitapp.api.model;

import android.content.Context;

import androidx.paging.PagedList;

import com.example.hci_onfitapp.api.AuthInterceptor;
import com.example.hci_onfitapp.api.data.RoutineData;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRoutine extends ApiService implements ApiRoutineService{
    private ApiRoutineService api;

    public ApiRoutine(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthInterceptor(context))
                .build();

        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(ApiRoutineService.class);
    }

    @Override
    public Single<PagedList<RoutineData>> getRoutines(Map<String, String> options) {
        return api.getRoutines(options);
    }

    @Override
    public Single<PagedList<RoutineData>> getUserRoutines(Map<String, String> options) {
        return api.getUserRoutines(options);
    }

//    @Override
//    public Single<PagedList<RoutineHistory>> getUserHistory(Map<String, String> options) {
//        return api.getUserHistory(options);
//    }

    @Override
    public Single<PagedList<RoutineData>> getFavouriteRoutines(Map<String, String> options) {
        return api.getFavouriteRoutines(options);
    }

//    @Override
//    public Single<RoutineData> rateRoutine(Integer routineId, RoutineRating rating) {
//        return api.rateRoutine(routineId, rating);
//    }

    @Override
    public Single<Response<Void>> favRoutine(Integer routineId) {
        return api.favRoutine(routineId);
    }

    @Override
    public Single<Response<Void>> unfavRoutine(Integer routineId) {
        return api.unfavRoutine(routineId);
    }

//    @Override
//    public Single<PagedList<ExerciseData>> getExercises(Integer routineId, Integer cycleId, Map<String, String> options) {
//        return api.getExercises(routineId, cycleId, options);
//    }
//
//    @Override
//    public Single<PagedList<RoutineCycleData>> getRoutineCycles(Integer routineId, Map<String, String> options) {
//        return api.getRoutineCycles(routineId, options);
//    }

//    @Override
//    public Single<RoutineData> addRoutineExecution(Integer routineId, RoutineExecution routineExecution) {
//        return api.addRoutineExecution(routineId, routineExecution);
//    }

    @Override
    public Single<RoutineData> getRoutineById(Integer routineId) {
        return api.getRoutineById(routineId);
    }
}
