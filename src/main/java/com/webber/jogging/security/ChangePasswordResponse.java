package com.webber.jogging.security;

public class ChangePasswordResponse {
    private final boolean isError;
    private final String message;

    public ChangePasswordResponse(boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }
}
