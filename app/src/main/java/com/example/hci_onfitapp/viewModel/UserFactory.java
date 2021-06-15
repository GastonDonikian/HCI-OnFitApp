package com.example.hci_onfitapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private final Application application;

    public UserFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == UserViewModel.class) {
            return (T) new UserViewModel(application);
        }
        return null;
    }
}
