package com.urakhov.dao;

import com.urakhov.domain.Genre;
import com.urakhov.domain.Style;

import javax.sql.DataSource;
import java.util.List;

public interface StyleDAO {
    void setDataSource(DataSource dataSource);
    void addStyle(String name, int id_genre);
    Style getStyleById(int id);
    void updateStyle(int id, String name, int genre_id);
    void removeStyle(int id);

    List<Style> getStyleList();
    List<Style> getStyleListJoinGenre();
}
