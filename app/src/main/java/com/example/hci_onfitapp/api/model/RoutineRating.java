package com.example.hci_onfitapp.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineRating {
    @Expose
    @SerializedName("score")
    private Integer score;

    @Expose
    @SerializedName("review")
    private String review;

    public RoutineRating(Integer score, String review) {
        this.score = score;
        this.review = review;
    }
}
