package com.example.hci_onfitapp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credentials {
    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
