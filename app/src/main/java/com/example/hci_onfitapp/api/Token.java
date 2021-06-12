package com.example.hci_onfitapp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @Expose
    @SerializedName("token")
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
