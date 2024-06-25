package com.webber.jogging.service;

import com.webber.jogging.domain.Activity;
import com.webber.jogging.domain.ActivityFilter;
import com.webber.jogging.domain.User;

import java.util.Date;
import java.util.List;

public interface ActivityService {
    /**
     * Create a new activity. The activity must not already be persistent, and its user must be set.
     *
     * @param activity  The activity to create.
     * @return  The activity after it has been made persistent.
     */
    Activity create(Activity activity);

    /**
     * Save a activity - this method is used to
     * modify an existing object.
     *
     * @param activity
     *          The activity to create or modify.
     * @return The activity after creation or modification.
     */
    Activity save(Activity activity);

    /**
     * Delete the given activity from persistent storage.
     *
     * @param activity  The activity to delete.
     */
    void delete(Activity activity);

    /**
     * Find the activity with the given ID
     *
     * @param id  The id
     * @return  The activity with that ID, or null if it doesn't exist.
     */
    Activity find(long id);

    /**
     * Load all activities from the database for the given user.
     * @param user The owner of the activities
     * @return  A List of all activities in the database belonging to the given user.
     */
    List<Activity> loadAll(User user);

    /**
     * Load a list of activities matching the given filter.
     * @param activityFilter  The filter criteria
     * @return  A list of activities matching the filter, or an empty list.
     */
    List<Activity> search(ActivityFilter activityFilter);

    /**
     * Get the total distance for the given date range.
     * @param startDate The start date of the date range, may not be <code>null</code>.
     * @param endDate  The end date of the date range, if null the total from the start till the present will be calculated
     * @param user The owner of the activities
     * @return  The total distance (presumed kilometers) activity within the date range.
     */
    double getDistanceForDateRange(Date startDate, Date endDate, User user);

}
