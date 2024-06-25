package com.webber.jogging.service;

import com.webber.jogging.domain.Gear;
import com.webber.jogging.domain.User;

import java.util.List;

public interface GearService {

    /**
     * Create a new persistent gear.
     * @param gear The gear, may not be already persistent
     * @return  The persisted gear.
     */
    Gear create(Gear gear);

    /**
     * Save an existing gear.
     * @param gear  The modified gear
     * @return The persisted gear
     */
    Gear save(Gear gear);

    /**
     * Find the gear with the given ID.
     * @param id  The id
     * @return The gear with that ID, or <code>null</code>
     */
    Gear find(long id);

    /**
     * Get the gear belonging to the given user.
     * @param user  The user
     * @param onlyActive  If <code>true</code>, only active gear
     * @return  The list of gear belonging to the given user, or an empty list.
     */
    List<Gear> getGearForUser(User user, boolean onlyActive);

}
