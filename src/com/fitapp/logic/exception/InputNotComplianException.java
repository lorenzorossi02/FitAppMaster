package com.fitapp.logic.exception;

public class InputNotComplianException extends Exception {
    private static final long serialVersionUID = -124633895052577366L;

    private static final String MESSAGE = "CHECK_DATA_INSERTED";
    private final Throwable cause = new Throwable(MESSAGE);

    public InputNotComplianException() {
        super(MESSAGE);
        this.initCause(cause);
    }
}
