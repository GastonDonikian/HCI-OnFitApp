package com.example.hci_onfitapp.api;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class User {
    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("lastName")
    private String lastName;

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

    @Expose
    @SerializedName("date")
    private String date;

    private Drawable profileImg;

    public User(String username, String password, String firstName, String lastName, String gender, Long birthdate, String email, String phone, String avatarUrl) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.date = new Date().toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "username=" + username +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email=" + email +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", date=" + date +
                '}';
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getDate(){
        return date;
    }

    public String getDaysTraining() {
        return findDifference(date, new Date().toString());
    }

    private String findDifference(String start_date, String end_date) {
        // SimpleDateFormat converts the
        Integer difference_In_Days = 0;
        // string format to date object
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(" dd MM yyyy HH:mm:ss");
        // Try Class
        try {
            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d2 = sdf.parse(end_date);
            Date d1 = sdf.parse(start_date);

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time = d2.getTime() - d1.getTime();
            difference_In_Days = (int)(TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365);
        }catch (ParseException e) {
            e.printStackTrace();
        } ;
        return difference_In_Days.toString();
    }
}
