package com.urakhov.dao.postgres;

import com.urakhov.dao.RespondentDAO;
import com.urakhov.domain.Respondent;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RespondentDaoImpl implements RespondentDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addRespondent(String surname, LocalDate birth, String gender, String email, String phone) {
        String SQL = "INSERT INTO respondent (surname, birth, gender, email, phone) VALUES (?,?,?,?,?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, surname);
            pr.setDate(2, Date.valueOf(birth));
            pr.setString(3, gender);
            if (email.equals(""))
                pr.setNull(4, Types.VARCHAR);
            else
                pr.setString(4, email);
            if (phone.equals(""))
                pr.setNull(5,Types.VARCHAR);
            else
                pr.setString(5, phone);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Respondent getRespondentById(int id) {
        String SQL = "SELECT * FROM respondent WHERE id=?";
        Respondent respondent = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            try (ResultSet rs = pr.executeQuery()) {
                 if (rs.next()) {
                     respondent = new Respondent(
                             rs.getInt("id"),
                             rs.getString("surname"),
                             rs.getDate("birth").toLocalDate(),
                             rs.getString("gender"),
                             rs.getString("email"),
                             rs.getString("phone")
                     );
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respondent;
    }

    @Override
    public void updateRespondent(int id, String surname, LocalDate birth, String gender, String email, String phone) {
        String SQL = "UPDATE respondent SET surname=?, birth=?, gender=?, email=?, phone=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, surname);
            pr.setDate(2, Date.valueOf(birth));
            pr.setString(3, gender);
            pr.setString(4, email);
            pr.setString(5, phone);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeRespondent(int id) {
        String SQL = "DELETE FROM respondent WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Respondent> getRespondentList() {
        String SQL = "SELECT * FROM respondent";
        List<Respondent> respondentsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(SQL)) {
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    Respondent respondent = new Respondent(
                            rs.getInt("id"),
                            rs.getString("surname"),
                            rs.getDate("birth").toLocalDate(),
                            rs.getString("gender"),
                            rs.getString("email"),
                            rs.getString("phone"));

                    respondentsList.add(respondent);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  respondentsList;
    }

}
