package com.webber.jogging.gpx;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.webber.jogging.activity.Activity;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserResource;
import jakarta.persistence.*;

@Entity
@Table(name = "gpx_tracks")
public class GpxTrack implements UserResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gpx_track", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String gpxTrack;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    @MapsId
    private Activity activity;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // For JPA
    protected GpxTrack() {
        super();
    }

    public GpxTrack(String gpxTrack, Activity activity, User user) {
        this.gpxTrack = gpxTrack;
        this.activity = activity;
        this.user = user;
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "GpxTrack{" +
                "id=" + id +
                ", gpxTrack='" + gpxTrack + '\'' +
                ", activity=" + activity +
                ", user=" + user +
                '}';
    }
}
