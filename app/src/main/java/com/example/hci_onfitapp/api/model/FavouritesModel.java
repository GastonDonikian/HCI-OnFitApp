package com.example.hci_onfitapp.api.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.hci_onfitapp.api.data.RoutineData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class FavouritesModel extends AndroidViewModel {

    public FavouritesModel(@androidx.annotation.NonNull Application application) {
        super(application);
        routinesService = new ApiRoutine(application) {
        };
    }
    private MutableLiveData<List<RoutineData>> favouriteRoutines = new MutableLiveData<>();

    private ApiRoutine routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public void favRoutine(int routineId) {
        disposable.add(routinesService.favRoutine(routineId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }
    public void unfavRoutine(int routineId) {
        disposable.add(routinesService.unfavRoutine(routineId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }
}
