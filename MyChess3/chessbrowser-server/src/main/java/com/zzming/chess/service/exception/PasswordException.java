package com.zzming.chess.service.exception;

public class PasswordException extends RuntimeException {

    private static final long serialVersionUID = -586725189741443782L;

    public PasswordException() {
        super();
    }

    public PasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(Throwable cause) {
        super(cause);
    }
    
}
