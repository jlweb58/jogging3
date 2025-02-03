package com.webber.jogging.security;

public class PasswordChangeRequest {
    private final String oldPassword;

    private final String newPassword;

    public PasswordChangeRequest(String oldPassword, String newPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
