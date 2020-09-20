package com.fitapp.logic.exception;

import javax.mail.MessagingException;

public class EmailException extends MessagingException {
    private static final long serialVersionUID = -8919891651981321898L;

    private static final String MESSAGE = "MESSAGE_NOT_SEND";
    private final Throwable cause = new Throwable(MESSAGE);

    public EmailException() {
        super(MESSAGE);
        this.initCause(cause);
    }
}
