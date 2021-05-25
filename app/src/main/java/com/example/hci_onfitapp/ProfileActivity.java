package com.example.hci_onfitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Funciona esto?");
        setContentView(R.layout.activity_profile);
    }
}