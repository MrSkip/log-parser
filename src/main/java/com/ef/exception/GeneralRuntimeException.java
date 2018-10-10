package com.ef.exception;

public class GeneralRuntimeException extends RuntimeException {

    public GeneralRuntimeException() {
        super("General application exception");
    }

    public GeneralRuntimeException(String message) {
        super(message);
    }

    public GeneralRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
