package com.fitapp.logic.exception;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = -1482962286272110475L;
    private static final String MESSAGE = "USER_NOT_FOUND";
    private final Throwable cause = new Throwable(MESSAGE);

    public UserNotFoundException() {
        super(MESSAGE);
        this.initCause(cause);
    }

}
