package com.example.hci_onfitapp.api.model;

import com.example.hci_onfitapp.api.ApiResponse;
import com.example.hci_onfitapp.api.Credentials;
import com.example.hci_onfitapp.api.Token;
import com.example.hci_onfitapp.api.User;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("users/login")
    Single<Token> login(@Body Credentials credentials);

    @POST("users/logout")
    Single<ApiResponse<Void>> logout();

    @GET("users/current")
    Single<User> getCurrent();

    @POST("user")
    Single<User> register(@Body User userInfo);

   // @POST("user/verify_email")
   // Single<ApiResponse<Void>> verifyEmail(@Body VerificationData credentials);

    @POST("user/resend_verification")
    Single<ApiResponse<Void>> resendVerification(@Body Map<String, String> data);


}
