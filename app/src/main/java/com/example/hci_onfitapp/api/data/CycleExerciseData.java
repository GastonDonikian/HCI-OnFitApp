package com.example.hci_onfitapp.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CycleExerciseData {
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("repetitions")
    @Expose
    private int repetitions;
    @SerializedName("exercise")
    @Expose
    private ExerciseData exercise;

    public CycleExerciseData() {
    }
    public CycleExerciseData(int order, int duration, int repetitions, ExerciseData exercise) {
        super();
        this.order = order;
        this.duration = duration;
        this.repetitions = repetitions;
        this.exercise = exercise;
    }



    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public String getRepetitionsString(){
        return String.valueOf(repetitions);
    }
    public String getDurationString(){
        return String.valueOf(duration);
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public ExerciseData getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseData exercise) {
        this.exercise = exercise;
    }
}
