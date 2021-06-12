package com.example.hci_onfitapp.api;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

//Call<T>
//LiveData<ApiResponse<T>>

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
       if(getRawType(returnType) != LiveData.class){
           return null;
       }
       Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
       Class<?> rawObservationType = getRawType(observableType);
       if(rawObservationType != ApiResponse.class){
           throw new IllegalArgumentException("type must be an API ");
       }
       if(!(observableType instanceof ParameterizedType)){
           throw new IllegalArgumentException("API response must be parametrizded");
       }
       Type bodyType = getParameterUpperBound( 0, (ParameterizedType) observableType);
       return new LiveDataCallAdapter(bodyType);
    }
}
