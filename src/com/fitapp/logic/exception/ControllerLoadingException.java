package com.fitapp.logic.exception;

import java.io.IOException;

public class ControllerLoadingException extends IOException {

    private static final long serialVersionUID = -3705081310533594953L;

    private final Throwable cause = new Throwable("CONTROLLER_NOT_LOADING");

    public ControllerLoadingException() {
        super("CONTROLLER_NOT_LOADING");
        this.initCause(cause);
    }
}
