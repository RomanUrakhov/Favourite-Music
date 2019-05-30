package com.urakhov.dao;

import com.urakhov.domain.User;
import com.urakhov.exceptions.UnknownUserException;

import javax.sql.DataSource;

public interface UserDao {
    void setDataSource(DataSource dataSource);
    User getUser(String login) throws UnknownUserException;
}
