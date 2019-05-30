package com.urakhov.dao.postgres;

import com.urakhov.dao.GenreDAO;
import com.urakhov.domain.Genre;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoImpl implements GenreDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addGenre(String name) {
        String SQL = "INSERT INTO genre (name) VALUES(?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, name);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Genre getGenreById(int id) {
        String SQL = "SELECT * FROM genre WHERE id=?";
        Genre genre = null;

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            try(ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    genre = new Genre(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }

    @Override
    public void updateGenre(int id, String name) {
        String SQL = "UPDATE genre SET name=? WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, name);
            pr.setInt(2, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeGenre(int id) {
        String SQL = "DELETE FROM genre WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Genre> getGenreList() {
        String SQL = "SELECT * FROM genre";
        List<Genre> genreList = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                    genreList.add(genre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genreList;
    }
}
