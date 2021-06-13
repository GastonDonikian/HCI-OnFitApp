package com.example.hci_onfitapp.api.model;

import android.content.Context;

import com.example.hci_onfitapp.api.ApiDateTypeConverter;
import com.example.hci_onfitapp.api.ApiResponse;
import com.example.hci_onfitapp.api.AuthInterceptor;
import com.example.hci_onfitapp.api.Credentials;
import com.example.hci_onfitapp.api.Token;
import com.example.hci_onfitapp.api.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient extends ApiService implements ApiUserService{

    private ApiUserService api;
    public ApiClient(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new ApiDateTypeConverter())
                .create();
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient.class);
    }

    public ApiUserService getApi(){
        return api;
    }


    @Override
    public Single<Token> login(Credentials credentials) {
        return api.login(credentials);
    }

    @Override
    public Single<ApiResponse<Void>> logout() {
        return api.logout();
    }

    @Override
    public Single<User> getCurrent() {
        return api.getCurrent();
    }

    @Override
    public Single<User> register(User userInfo) {
        return api.register(userInfo);
    }

    @Override
    public Single<ApiResponse<Void>> resendVerification(Map<String, String> data) {
        return api.resendVerification(data);
    }
}
