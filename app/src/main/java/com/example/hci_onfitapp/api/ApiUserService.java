package com.example.hci_onfitapp.api;

import android.media.session.MediaSession;

import androidx.lifecycle.LiveData;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("users/login")
   LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrent();

}
