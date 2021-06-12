package com.example.hci_onfitapp;

import android.app.Application;

public class App extends Application {

    private static AppPreferences preferences;

    public static AppPreferences getPreferences() {
        return preferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new AppPreferences(this);
    }
}
