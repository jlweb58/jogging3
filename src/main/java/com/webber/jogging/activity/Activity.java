package com.webber.jogging.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webber.jogging.domain.Gear;
import com.webber.jogging.domain.User;
import com.webber.jogging.domain.UserResource;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Activity implements UserResource {

    private static final long serialVersionUID = -8506894102933517235L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "course", nullable = true)
    private String course;

    @Embedded
    private ActivityDuration activityDuration = new ActivityDuration(0, 0, 0);

    @Column(name = "distance", nullable = false)
    private double distance;

    @Column(name = "comments", nullable = true)
    private String comments = "";

    @Column(name = "weather", nullable = true)
    private String weather;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "avgheartrate", nullable = true)
    private Integer avgHeartRate;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "gearid", nullable = true)
    private Gear gear;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "activitytype", nullable = true)
    private ActivityType activityType;

    public Long getId() {
        return id;
    }

    Activity(Date date, String course, double distance, ActivityDuration activityDuration,
             String weather, String comments, Integer avgHeartRate, ActivityType activityType) {
        this.date = date;
        this.course = course;
        this.distance = distance;
        this.activityDuration = activityDuration;
        this.weather = weather;
        this.comments = comments;
        this.avgHeartRate = avgHeartRate;
        this.activityType = activityType;
    }

    public static Activity build(Date date, String course, double distance, ActivityDuration activityDuration,
                                 String weather, String comments, Integer avgHeartRate, User user, ActivityType activityType) {
        Activity activity = new Activity(date, course, distance, activityDuration, weather, comments, avgHeartRate, activityType);
        activity.setUser(user);
        return activity;
    }

    protected Activity() {

        //needed for JPA
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String name) {
        this.course = name;
    }

    public ActivityDuration getActivityDuration() {
        if (activityDuration == null) {
            return new ActivityDuration(0, 0, 0);
        }
        return activityDuration;
    }

    public void setActivityDuration(ActivityDuration activityDuration) {
        if (activityDuration == null) {
            activityDuration = new ActivityDuration(0, 0, 0);
        }
        this.activityDuration = activityDuration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAvgHeartRate() {
        return avgHeartRate;
    }

    public void setAvgHeartRate(Integer avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }

    @Override
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + getId() + ", course=" + course + ", activityDuration=" + activityDuration + ", distance=" + distance + ", comments="
                + comments + ", weather=" + weather + ", date=" + date + ", avgHeartRate=" + avgHeartRate + ", gear=" + gear + ", user="
                + user + ", activityType=" + activityType
                + '}';
    }


}
