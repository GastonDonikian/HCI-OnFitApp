package com.example.hci_onfitapp.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.hci_onfitapp.AppPreferences;
import com.example.hci_onfitapp.api.ApiError;
import com.example.hci_onfitapp.api.Credentials;
import com.example.hci_onfitapp.api.Token;
import com.example.hci_onfitapp.api.User;
import com.example.hci_onfitapp.api.model.ApiClient;
import com.example.hci_onfitapp.api.model.ApiService;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<User> userInfo = new MutableLiveData<>();
    private MutableLiveData<Token> token = new MutableLiveData<>();
    private MutableLiveData<Boolean> verified = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private MutableLiveData<ApiError> loginError = new MutableLiveData<>();
    private MutableLiveData<ApiError> registerError = new MutableLiveData<>();

    private ApiClient userService;
    private Application app;

    public UserViewModel(@androidx.annotation.NonNull Application application){
        super(application);
        userService = new ApiClient(application);
        app = application;
    }

    public void tryLogin(String username, String password) {
        Credentials credentials = new Credentials(username, password);
        loading.setValue(true);
        disposable.add(userService.login(credentials)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Token>() {

                    @Override
                    public void onSuccess(@NonNull Token authToken) {
                        token.setValue(authToken);
                        ApiService.setAuthToken(authToken.getToken());
                        AppPreferences preferences = new AppPreferences(app);
                        preferences.setAuthToken(authToken.getToken());
                        loginError.setValue(null);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            try {
                                Gson gson = new Gson();
                                ApiError error;
                                error = gson.fromJson(httpException.response().errorBody().string(), new TypeToken<ErrorResponse>() {
                                }.getType());
                                loginError.setValue(error);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        e.printStackTrace();
                        loading.setValue(false);
                    }
                })
        );
    }
}
