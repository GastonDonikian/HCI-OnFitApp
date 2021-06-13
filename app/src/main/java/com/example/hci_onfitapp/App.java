package com.example.hci_onfitapp;

import android.app.Application;

import com.example.hci_onfitapp.api.model.ApiUserService;

public class App extends Application {

    private static AppPreferences preferences;
    private static ApiUserService userService;

    public static AppPreferences getPreferences() {
        return preferences;
    }
    public static ApiUserService getUserService(){
        return userService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new AppPreferences(this);
    }
}
