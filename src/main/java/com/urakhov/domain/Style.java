package com.urakhov.domain;

import javax.sql.DataSource;

public class Style {
    private int id;
    private String name;
    private int id_genre;
    private String name_genre;
    private Genre genre;


    public Style() {}

    public Style(int id, String name, int id_genre) {
        this.id = id;
        this.name = name;
        this.id_genre = id_genre;
    }
    public Style(int id, String name, Genre genre) {
        this.id = id;
        this.name = name;
        this.id_genre = genre.getId();
        this.genre = genre;
    }

    public String getName_genre() {
        return name_genre;
    }

    public void setName_genre(String name_genre) {
        this.name_genre = name_genre;
    }

    public Style(int id, String name, int id_genre, String name_genre) {
        this.id = id;
        this.name = name;
        this.id_genre = id_genre;
        this.name_genre = name_genre;
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

    public int getId_genre() {
        return id_genre;
    }

    public void setId_genre(int id_genre) {
        this.id_genre = id_genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return getName();
    }
}
