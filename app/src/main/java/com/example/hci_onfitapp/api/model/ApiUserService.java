package com.example.hci_onfitapp.api.model;

import com.example.hci_onfitapp.api.ApiResponse;
import com.example.hci_onfitapp.api.Credentials;
import com.example.hci_onfitapp.api.Token;
import com.example.hci_onfitapp.api.User;
import com.example.hci_onfitapp.api.Verification;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("users/login")
    Single<Token> login(@Body Credentials credentials);

    @POST("users/logout")
    Single<Response<Void>> logout();

    @GET("users/current")
    Single<User> getCurrent();

    @POST("users")
    Single<User> register(@Body User userInfo);

    @POST("users/verify_email")
    Single<Response<Void>> verifyEmail(@Body Verification verification);

    @POST("users/resend_verification")
    Single<Response<Void>> resendVerification(@Body Map<String, String> data);


}
