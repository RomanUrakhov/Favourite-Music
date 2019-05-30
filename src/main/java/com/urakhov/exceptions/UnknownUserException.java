package com.urakhov.exceptions;

public class UnknownUserException extends Exception {
    private String login;

    public UnknownUserException(String login) {
        super("Cannot find user with such login");
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
