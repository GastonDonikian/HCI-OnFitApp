package com.example.hci_onfitapp;


public class RoutineCardData {

    private String title;
    private int duration;

    public RoutineCardData(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return title;
    }
}