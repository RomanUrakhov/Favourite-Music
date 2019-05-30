package com.urakhov.dao.postgres;

import com.urakhov.dao.StyleDAO;
import com.urakhov.domain.Genre;
import com.urakhov.domain.Style;
import javafx.collections.FXCollections;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StyleDaoImpl implements StyleDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addStyle(String name, int id_genre) {
        String SQL = "INSERT INTO style (id_genre, name) VALUES(?,?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id_genre);
            pr.setString(2, name);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Style getStyleById(int id) {
        String SQL = "SELECT * FROM style WHERE id=?";
        Style style = null;

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            try(ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    style = new Style(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("id_genre")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return style;
    }

    @Override
    public void updateStyle(int id, String name, int id_genre) {
        String SQL = "UPDATE style SET name=?, id_genre=? WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, name);
            pr.setInt(2, id_genre);
            pr.setInt(3, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStyle(int id) {
        String SQL = "DELETE FROM style WHERE id=?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setInt(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Style> getStyleList() {
        String SQL = "SELECT * FROM style";
        List<Style> styleList = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            ResultSet rs = pr.executeQuery();
                while (rs.next()) {
                    Style style = new Style(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("id_genre")
                    );
                    styleList.add(style);
                }
                rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return styleList;
    }

    @Override
    public List<Style> getStyleListJoinGenre() {
        String SQL = "SELECT style.id, style.name, style.id_genre, genre.name AS name_genre FROM style INNER JOIN genre ON style.id_genre = genre.id";
        List<Style> styleList = new ArrayList<>();

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Style style = new Style(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("id_genre"),
                        rs.getString("name_genre")
                );
                styleList.add(style);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return styleList;
    }
}
