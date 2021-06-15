package com.example.hci_onfitapp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verification {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("code")
    @Expose
    private String code;


    public Verification(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
