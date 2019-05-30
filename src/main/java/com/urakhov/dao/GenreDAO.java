package com.urakhov.dao;

import com.urakhov.domain.Genre;

import javax.sql.DataSource;
import java.util.List;

public interface GenreDAO {
    void setDataSource(DataSource dataSource);
    void addGenre(String name);
    Genre getGenreById(int id);
    void updateGenre(int id, String name);
    void removeGenre(int id);

    List<Genre> getGenreList();
}
