package com.urakhov.dao.postgres;

import com.urakhov.dao.RatingDAO;
import com.urakhov.domain.Rating;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingDaoImpl implements RatingDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void removeRating(int id_category) {
        String SQL ="DELETE FROM rating WHERE id_category=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id_category);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rating> getGeneralRatingList() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=7";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Rating> getFirstTenPopularSongs() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=7 ORDER BY value DESC LIMIT 10";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Rating> getMenRatingList() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=1";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Rating> getWomenRatingList() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=2";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Rating> getTenRatingList() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=3";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Rating> getSixteenRatingList() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=4";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Rating> getTwentytwoRatingList() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=5";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Rating> getThirtySixRatingList() {
        String SQL = "SELECT rating.id, rating.id_song, song.name AS name_song, rating.id_category, rating.value, rating.votes FROM rating" +
                " INNER JOIN song ON id_song=song.id AND id_category=6";
        List<Rating> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Rating(
                            rs.getInt("id"),
                            (float) rs.getDouble("value"),
                            rs.getInt("id_song"),
                            rs.getString("name_song"),
                            rs.getInt("id_category"),
                            rs.getInt("votes")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<String, Float> getGenreRatingMap() {
        String SQL = "SELECT DISTINCT g.name, sum(r.value) FROM rating AS r " +
                "INNER JOIN song AS s ON r.id_song=s.id " +
                "INNER JOIN style AS st ON s.id_style=st.id " +
                "INNER JOIN genre AS g ON g.id=st.id_genre AND r.id_category=7 GROUP BY g.name";
        HashMap<String, Float> result = new HashMap<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getString("name"), rs.getFloat("sum"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
