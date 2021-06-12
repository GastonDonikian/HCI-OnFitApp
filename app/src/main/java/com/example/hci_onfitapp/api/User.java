package com.example.hci_onfitapp.api;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class User {
    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("fullName")
    private String fullName;

    @Expose
    @SerializedName("gender")
    private String gender;

    @Expose
    @SerializedName("birthdate")
    private Long birthdate;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("avatarUrl")
    private String avatarUrl;

    private Drawable profileImg;

    public User(String username, String password, String fullName, String gender, Long birthdate, String email, String phone, String avatarUrl) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "userame: "+username;
    }

    public Drawable getProfileImg() {
        return profileImg;
    }

    public String getBirthdateString(){
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.US);
        return dateFormat.format(birthdate);
    }

    public void setProfileImg(Drawable profileImg) {
        this.profileImg = profileImg;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public Long getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}
