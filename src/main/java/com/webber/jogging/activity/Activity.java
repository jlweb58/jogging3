package com.webber.jogging.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webber.jogging.gear.Gear;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserResource;

import com.webber.jogging.util.DurationConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "activities")
public class Activity implements UserResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "course", nullable = false)
    private String course;

    @Convert(converter = DurationConverter.class)
    @Column(name = "duration")
    private Duration duration;

    @Column(name = "distance")
    private double distance;

    @Column(name = "comments")
    private String comments = "";

    @Column(name = "weather")
    private String weather;

    @Column(name = "activity_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "avg_heart_rate")
    private Integer avgHeartRate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gear_id")
    private Gear gear;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    private ActivityType activityType;

    Activity(Date date, String course, double distance, Duration duration,
             String weather, String comments, Integer avgHeartRate, ActivityType activityType) {
        this.date = date;
        this.course = course;
        this.distance = distance;
        this.duration = duration;
        this.weather = weather;
        this.comments = comments;
        this.avgHeartRate = avgHeartRate;
        this.activityType = activityType;
    }

    public static Activity build(Date date, String course, double distance, Duration activityDuration,
                                 String weather, String comments, Integer avgHeartRate, User user, ActivityType activityType) {
        Activity activity = new Activity(date, course, distance, activityDuration, weather, comments, avgHeartRate, activityType);
        activity.setUser(user);
        return activity;
    }

    protected Activity() {
        //needed for JPA
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + getId() + ", course=" + course + ", duration=" + duration + ", distance=" + distance + ", comments="
                + comments + ", weather=" + weather + ", date=" + date + ", avgHeartRate=" + avgHeartRate + ", gear=" + gear + ", user="
                + user + ", activityType=" + activityType
                + '}';
    }


}
