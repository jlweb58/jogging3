package com.webber.jogging.user;

/**
 *
 * Implementers of this interface indicate that they are a resource with a many-to-one
 * or one-to-one relation to a user, and need to be access controlled (e.g. to prevent one
 * authenticated user from seeing another user's properties
 */
public interface UserResource {

    /**
     * Get the owner of this resource
     * @return  The owner of this resource, or <code>null</code> if there is no owner.
     */
    User getUser();

}
