package com.example.hci_onfitapp.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.hci_onfitapp.AppPreferences;
import com.example.hci_onfitapp.api.ApiError;
import com.example.hci_onfitapp.api.Credentials;
import com.example.hci_onfitapp.api.Token;
import com.example.hci_onfitapp.api.User;
import com.example.hci_onfitapp.api.Verification;
import com.example.hci_onfitapp.api.model.ApiClient;
import com.example.hci_onfitapp.api.model.ApiService;
import com.example.hci_onfitapp.api.model.ApiUserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

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

    public void logout() {
        disposable.add(userService.logout()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        new AppPreferences(app).setAuthToken(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
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
                                error = gson.fromJson(httpException.response().errorBody().string(), new TypeToken<ApiError>() {
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

    public void resendVerification(String email) {
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        loading.setValue(true);
        disposable.add(userService.resendVerification(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        loading.setValue(false);
                    }
                })
        );
    }

    public void setUserData() {
        disposable.add(userService.getCurrent()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    public void onSuccess(@NonNull User info) {
                        userInfo.setValue(info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    public void tryRegister(User data) {
        loading.setValue(true);

        disposable.add((userService.register(data))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(@NonNull User info) {

                        userInfo.setValue(info);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            try {
                                Gson gson = new Gson();
                                ApiError error;
                                error = gson.fromJson(httpException.response().errorBody().string(), new TypeToken<ApiError>() {
                                }.getType());
                                registerError.setValue(error);
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

    public void verifyUser(String code) {
        loading.setValue(true);
        System.out.println(userInfo.getValue().getEmail());
        System.out.println(code);
        disposable.add(userService.verifyEmail(new Verification(userInfo.getValue().getEmail(), code))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        verified.setValue(true);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        verified.setValue(false);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }



    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }

    public MutableLiveData<Boolean> getVerified() {
        return verified;
    }

    public MutableLiveData<User> getUserData() {
        return userInfo;
    }

    public MutableLiveData<Token> getToken() {
        return token;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<ApiError> getLoginError() {
        return loginError;
    }

    public MutableLiveData<ApiError> getRegisterError() {
        return registerError;
    }

    public void setLoginErrorCode(ApiError error) {
        loginError.setValue(error);
    }

    public void setRegisterError(ApiError error) {
        registerError.setValue(error);
    }
}
