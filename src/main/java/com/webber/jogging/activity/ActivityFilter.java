package com.webber.jogging.activity;

import com.webber.jogging.domain.User;

import java.io.Serializable;
import java.util.Date;


/**
 * Encapsulates queries for activities.
 */
public class ActivityFilter implements Serializable {


    private static final long serialVersionUID = -2492458006059511810L;

    private final String course;

    private final Date startDate;

    private final Date endDate;

    private final User user;

    /***
     * Create an activity filter with the given parameters, any of which may be null.
     *
     * @param course    The course to match
     * @param startDate The start date (inclusive)
     * @param endDate   The end date (exclusive)
     * @param user      The owner of the activity
     */
    public ActivityFilter(String course, Date startDate, Date endDate, User user) {
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }

    public String getCourse() {
        return course;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public User getUser() {
        return user;
    }
}
