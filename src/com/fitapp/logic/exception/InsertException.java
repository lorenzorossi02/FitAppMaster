package com.fitapp.logic.exception;

public class InsertException extends Exception {

    private static final long serialVersionUID = 7244571908790437169L;

    private static final String MESSAGE = "FAILED_TO_INSERT_DATA";
    private final Throwable cause = new Throwable(MESSAGE);

    public InsertException() {
        super(MESSAGE);
        this.initCause(cause);
    }

}
