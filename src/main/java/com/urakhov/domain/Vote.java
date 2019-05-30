package com.urakhov.domain;

import java.time.LocalDate;

public class Vote {
    private int id;
    private int id_respondent;
    private int id_song;
    private LocalDate date;
    private float assessment;

    public Vote() {}

    public Vote(int id, int id_respondent, int id_song, LocalDate date, float assessment) {
        this.id = id;
        this.id_respondent = id_respondent;
        this.id_song = id_song;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_respondent() {
        return id_respondent;
    }

    public void setId_respondent(int id_respondent) {
        this.id_respondent = id_respondent;
    }

    public int getId_song() {
        return id_song;
    }

    public void setId_song(int id_song) {
        this.id_song = id_song;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getAssessment() {
        return assessment;
    }

    public void setAssessment(float assessment) {
        this.assessment = assessment;
    }
}
