package com.example.hci_onfitapp;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private  static final String PREFERENCES_NAME = "my-preferences";
    private static final String AUTH_TOKEN = "auth_token";
    private final SharedPreferences sharedPreferences;
    public AppPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
    public void setAuthToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken(){
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }
}
