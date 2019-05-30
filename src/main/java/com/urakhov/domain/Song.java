package com.urakhov.domain;

import java.sql.Time;

public class Song {
    private int id;
    private String name;
    private Time duration;
    private int id_style;
    private String name_style;

    public Song() {}

    public Song(int id, String name, Time duration, int id_style) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.id_style = id_style;
    }

    public String getName_style() {
        return name_style;
    }

    public void setName_style(String name_style) {
        this.name_style = name_style;
    }

    public Song(int id, String name, Time duration, int id_style, String name_style) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.id_style = id_style;
        this.name_style = name_style;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public int getId_style() {
        return id_style;
    }

    public void setId_style(int id_style) {
        this.id_style = id_style;
    }

    @Override
    public String toString() {
        return getName();
    }
}
