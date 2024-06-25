package com.webber.jogging.service;

import com.webber.jogging.domain.Activity;
import com.webber.jogging.domain.ActivityFilter;
import com.webber.jogging.domain.User;

import java.util.Date;
import java.util.List;

public interface ActivityService {
    /**
     * Create a new run. The run must not already be persistent, and its user must be set.
     *
     * @param activity  The run to create.
     * @return  The run after it has been made persistent.
     */
    Activity create(Activity activity);

    /**
     * Save a run - this method is used to
     * modify an existing object.
     *
     * @param activity
     *          The run to create or modify.
     * @return The run after creation or modification.
     */
    Activity save(Activity activity);

    /**
     * Delete the given run from persistent storage.
     *
     * @param activity  The run to delete.
     */
    void delete(Activity activity);

    /**
     * Find the run with the given ID
     *
     * @param id  The id
     * @return  The run with that ID, or null if it doesn't exist.
     */
    Activity find(long id);

    /**
     * Load all runs from the database for the given user.
     * @param user The owner of the runs
     * @return  A List of all runs in the database belonging to the given user.
     */
    List<Activity> loadAll(User user);

    /**
     * Load a list of runs matching the given filter.
     * @param activityFilter  The filter criteria
     * @return  A list of runs matching the filter, or an empty list.
     */
    List<Activity> search(ActivityFilter activityFilter);

    /**
     * Get the total distance for the given date range.
     * @param startDate The start date of the date range, may not be <code>null</code>.
     * @param endDate  The end date of the date range, if null the total from the start till the present will be calculated
     * @param user The owner of the runs
     * @return  The total distance (presumed kilometers) run within the date range.
     */
    double getDistanceForDateRange(Date startDate, Date endDate, User user);

}
