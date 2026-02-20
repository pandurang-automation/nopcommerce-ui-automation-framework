package com.ultron.framework.exceptions;

public class DataException extends FrameworkException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}