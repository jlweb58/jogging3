package com.webber.jogging.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Run extends AbstractPersistable<Long> implements UserResource {

    private static final long serialVersionUID = -8506894102933517235L;

    @Column(name = "course", nullable = true)
    private String course;

    @Embedded
    private RunDuration runDuration = new RunDuration(0, 0, 0);

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
    @JoinColumn(name = "shoesid", nullable = true)
    private Shoes shoes;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "gpxtrack", nullable = true, columnDefinition = "MEDIUMTEXT")
    private String gpxTrack;

    Run(Date date, String course, double distance, RunDuration runDuration,
               String weather, String comments, Integer avgHeartRate) {
        this.date = date;
        this.course = course;
        this.distance = distance;
        this.runDuration = runDuration;
        this.weather = weather;
        this.comments = comments;
        this.avgHeartRate = avgHeartRate;
    }

    public static Run build(Date date, String course, double distance, RunDuration runDuration,
                            String weather, String comments, Integer avgHeartRate, User user) {
        Run run = new Run(date, course, distance, runDuration, weather, comments, avgHeartRate);
        run.setUser(user);
        return run;
    }

    public static Run build(Date date, double distance, RunDuration runDuration, User user) {
        return build(date, null, distance, runDuration, null, null, null, user);
    }

    protected Run() {

        //needed for JPA
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String name) {
        this.course = name;
    }

    public RunDuration getRunDuration() {
        if (runDuration == null) {
            return new RunDuration(0, 0, 0);
        }
        return runDuration;
    }

    public void setRunDuration(RunDuration runDuration) {
        if (runDuration == null) {
            runDuration = new RunDuration(0, 0, 0);
        }
        this.runDuration = runDuration;
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

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
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

    public String getGpxTrack() {
        return gpxTrack;
    }

    public void setGpxTrack(String gpxTrack) {
        this.gpxTrack = gpxTrack;
    }

    @Override
    public String toString() {
        return "Run{" + "id=" + getId() + ", course=" + course + ", runDuration=" + runDuration + ", distance=" + distance + ", comments="
                + comments + ", weather=" + weather + ", date=" + date + ", avgHeartRate=" + avgHeartRate + ", shoes=" + shoes + ", user=" + user
                + '}';
    }


}
