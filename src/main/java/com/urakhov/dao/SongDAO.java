package com.urakhov.dao;

import com.urakhov.domain.Song;

import javax.sql.DataSource;
import java.sql.Time;
import java.util.List;

public interface SongDAO {
    void setDataSource(DataSource dataSource);
    void addSong(String name, Time duration, int id_style);
    Song getSongById(int id);
    void removeSong(int id);
    void updateSong(int id, String name, Time duration, int id_style);

    List<Song> getSongList();
    List<Song> getSongListJoinStyle();
}
