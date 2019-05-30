package com.urakhov.dao.postgres;

import com.urakhov.dao.SongDAO;
import com.urakhov.domain.Song;
import javafx.collections.ObservableList;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDaoImpl implements SongDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addSong(String name, Time duration, int id_style) {
        String SQL = "INSERT INTO song (name, duration, id_style) VALUES (?,?,?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, name);
            pr.setTime(2, duration);
            pr.setInt(3, id_style);

            pr.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Song getSongById(int id) {
        String SQL = "SELECT * FROM song WHERE id=?";
        Song song = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            try(ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    song = new Song(
                            id,
                            rs.getString("name"),
                            rs.getTime("duration"),
                            rs.getInt("id_style"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;
    }

    @Override
    public void removeSong(int id) {
        String SQL = "DELETE FROM song WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            pr.setInt(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSong(int id, String name, Time duration, int id_style) {
        String SQL = "UPDATE song SET name=?, duration=?, id_style=? WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, name);
            pr.setTime(2, duration);
            pr.setInt(3, id_style);
            pr.setInt(4, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Song> getSongList() {
        String SQL = "SELECT * FROM song";
        List<Song> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            try(ResultSet rs = pr.executeQuery()) {
                while(rs.next()) {
                    result.add(new Song(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTime("duration"),
                            rs.getInt("id_style")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Song> getSongListJoinStyle() {
        String SQL = "SELECT song.id, song.name, song.id_style, style.name AS name_style, song.duration FROM song INNER JOIN style " +
                "ON song.id_style = style.id";

        List<Song> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Song(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTime("duration"),
                            rs.getInt("id_style"),
                            rs.getString("name_style")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
