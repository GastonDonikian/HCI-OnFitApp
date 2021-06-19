package com.example.hci_onfitapp.api.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hci_onfitapp.api.ApiError;

import org.jetbrains.annotations.NotNull;

public class Resource<T> {
    @NonNull
    private final Status status;

    @Nullable
    private final ApiError error;

    @Nullable
    private final T data;

    @NotNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public ApiError getError() {
        return error;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable ApiError error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(ApiError error, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }
}
