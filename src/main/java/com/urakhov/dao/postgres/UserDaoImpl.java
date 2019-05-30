package com.urakhov.dao.postgres;

import com.urakhov.dao.UserDao;
import com.urakhov.domain.User;
import com.urakhov.exceptions.UnknownUserException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getUser(String login) throws UnknownUserException {
        String SQL = "SELECT * FROM users WHERE login=?";
        User result = null;

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement(SQL)) {
            pr.setString(1, login);

            try(ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    result = new User(
                            rs.getInt("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("salt")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result==null) throw new UnknownUserException(login);
        return result;
    }
}
