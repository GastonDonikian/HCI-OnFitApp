package com.example.hci_onfitapp;

import android.app.Application;

import com.example.hci_onfitapp.api.RoutineRepository;
import com.example.hci_onfitapp.api.model.ApiUserService;

public class App extends Application {

    private static AppPreferences preferences;
    private static ApiUserService userService;
    private RoutineRepository routineRepository;

    public static AppPreferences getPreferences() {
        return preferences;
    }
    public static ApiUserService getUserService(){
        return userService;
    }
    public RoutineRepository getRoutineRepository(){
        return routineRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new AppPreferences(this);
        routineRepository = new RoutineRepository(this);
    }
}
