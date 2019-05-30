package com.urakhov.dao.postgres;

import com.urakhov.dao.PollDAO;
import com.urakhov.domain.Vote;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PollDaoImpl implements PollDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addVote(int id_respondent, int id_song, float assessment) {
        String SQL = "INSERT INTO poll (id_respondent, id_song, assessment) VALUES(?,?,?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id_respondent);
            pr.setInt(2, id_song);
            pr.setFloat(3, assessment);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vote getVoteById(int id) {
        String SQL = "SELECT * FROM poll WHERE id=?";
        Vote result = null;

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            try(ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    result = new Vote(
                            rs.getInt("id"),
                            rs.getInt("id_respondent"),
                            rs.getInt("id_song"),
                            rs.getDate("date").toLocalDate(),
                            rs.getFloat("assessment")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void removeVote(int id) {
        String SQL = "DELETE FROM poll WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVote(int id, int id_respondent, int id_song) {
        String SQL = "UPDATE poll SET id_respondent=?, id_song=? WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id_respondent);
            pr.setInt(2, id_song);
            pr.setInt(3, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vote> getAllVotes() {
        String SQL = "SELECT * FROM poll";
        List<Vote> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Vote(
                            rs.getInt("id"),
                            rs.getInt("id_respondent"),
                            rs.getInt("id_song"),
                            rs.getDate("date").toLocalDate(),
                            rs.getFloat("assessment")
                    ));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Vote> getVotesByRespondentId(int id_respondent) {
        String SQL = "SELECT * FROM poll WHERE id_respondent=?";
        List<Vote> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id_respondent);
            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Vote(
                            rs.getInt("id"),
                            rs.getInt("id_respondent"),
                            rs.getInt("id_song"),
                            rs.getDate("date").toLocalDate(),
                            rs.getFloat("assessment")
                    ));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Vote> getVotesBySong(int id_song) {
        String SQL = "SELECT * FROM poll WHERE id_song=?";
        List<Vote> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1,id_song);
            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Vote(
                            rs.getInt("id"),
                            rs.getInt("id_respondent"),
                            rs.getInt("id_song"),
                            rs.getDate("date").toLocalDate(),
                            rs.getFloat("assessment")
                    ));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Vote> getVotesByDate(LocalDate date) {
        String SQL = "SELECT * FROM poll WHERE date=?";
        List<Vote> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setDate(1, Date.valueOf(date));
            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Vote(
                            rs.getInt("id"),
                            rs.getInt("id_respondent"),
                            rs.getInt("id_song"),
                            rs.getDate("date").toLocalDate(),
                            rs.getFloat("assessment")
                    ));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Vote> getVotesByGender(String gender) {
        String SQL = "SELECT * FROM poll WHERE id_respondent IN (SELECT id FROM respondent WHERE gender=?)";
        List<Vote> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, gender);

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add( new Vote(
                            rs.getInt("id"),
                            rs.getInt("id_respondent"),
                            rs.getInt("id_song"),
                            rs.getDate("date").toLocalDate(),
                            rs.getFloat("assessment")
                    ));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Vote> getVotesByAge(List<LocalDate> ageInterval) {
        String SQL = "SELECT * FROM poll WHERE id_respondent IN (SELECT id FROM respondent WHERE birth BETWEEN ? AND ?)";
        List<Vote> result = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setDate(1, Date.valueOf(ageInterval.get(0)));
            pr.setDate(2, Date.valueOf(ageInterval.get(1)));

            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(new Vote(
                            rs.getInt("id"),
                            rs.getInt("id_respondent"),
                            rs.getInt("id_song"),
                            rs.getDate("date").toLocalDate(),
                            rs.getFloat("assessment")
                    ));
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
