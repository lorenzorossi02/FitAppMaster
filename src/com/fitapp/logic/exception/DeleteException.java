package com.fitapp.logic.exception;

public class DeleteException extends Exception {

    private static final long serialVersionUID = -9019837106454713955L;

    private static final String MESSAGE = "FAILED_TO_DELETE_DATA";
    private final Throwable cause = new Throwable(MESSAGE);

    public DeleteException() {
        super(MESSAGE);
        this.initCause(cause);
    }


}
