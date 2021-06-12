package com.example.hci_onfitapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.hci_onfitapp.api.ApiResponse;
import com.example.hci_onfitapp.api.Credentials;
import com.example.hci_onfitapp.api.Token;
import com.example.hci_onfitapp.api.User;

import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = findViewById(R.id.button_to_log_in);
        Credentials credentials = new Credentials();
        credentials.setUsername("johndoe");
        credentials.setPassword("123456790");
        LiveData<ApiResponse<Token>> token = App.getUserService().login(credentials);
        token.observe(()->{
            App.getPreferences().setAuthToken();
        });

        LiveData<ApiResponse<User>> userLiveData = App.getUserService().getCurrent();
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }


}
