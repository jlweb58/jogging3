package com.webber.jogging.service;

import com.webber.jogging.domain.User;

public interface UserService {
    /**
     * Find the user with the given username
     * @param username  The username (may not be null)
     * @return  The unique user with that username, or <code>null</code> if none is found.
     */
    User find(String username);

    /**
     * Create a new persistent user.
     * @param user  The user, may not be already persistent
     * @return  The persisted user
     */
    User create(User user);

    /**
     * Change the password of the given user
     *
     * @param user   The user whose password should be changed
     * @param oldPassword  The user's old password (will be verified)
     * @param newPassword  The user's new password (no special checks are made here as to its strength, may not be <code>null</code>)
     *
     * @throws SecurityException if the old password is not correct
     */
    void changePassword(User user, String oldPassword, String newPassword);

}
