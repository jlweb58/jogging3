package com.webber.jogging.service;

import com.webber.jogging.domain.Shoes;
import com.webber.jogging.domain.User;

import java.util.List;

public interface ShoesService {

    /**
     * Create a new persistent pair of shoes.
     * @param shoes The shoes, may not be already persistent
     * @return  The persisted shoes.
     */
    Shoes create(Shoes shoes);

    /**
     * Save an existing pair of shoes.
     * @param shoes  The modified shoes
     * @return The persisted shoes
     */
    Shoes save(Shoes shoes);

    /**
     * Find the shoes with the given ID.
     * @param id  The id
     * @return The shoes with that ID, or <code>null</code>
     */
    Shoes find(long id);

    /**
     * Get the shoes belonging to the given user.
     * @param user  The user
     * @param onlyActive  If <code>true</code>, only active shoes
     * @return  The list of shoes belonging to the given user, or an empty list.
     */
    List<Shoes> getShoesForUser(User user, boolean onlyActive);

}
