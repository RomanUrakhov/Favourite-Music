package com.urakhov.domain;

import java.time.LocalDate;

public class Rating {

    private int id;
    private float value;
    private int id_song;
    private String name_song;
    private int id_category;
    private int votes;

    public String getName_song() {
        return name_song;
    }

    public void setName_song(String name_song) {
        this.name_song = name_song;
    }

    public Rating(int id, float value, int id_song, int id_category, int votes) {
        this.id = id;
        this.value = value;
        this.id_song = id_song;
        this.id_category = id_category;
        this.votes = votes;
    }
    public Rating(int id, float value, int id_song, String name_song, int id_category, int votes) {
        this.id = id;
        this.value = value;
        this.id_song = id_song;
        this.id_category = id_category;
        this.votes = votes;
        this.name_song = name_song;
    }
    public Rating() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getId_song() {
        return id_song;
    }

    public void setId_song(int id_song) {
        this.id_song = id_song;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
