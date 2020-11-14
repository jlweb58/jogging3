package com.webber.jogging.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Table(name = "gpx_track")
public class GpxTrack implements UserResource {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "gpxtrack", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String gpxTrack;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    @MapsId
    private Run run;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnore
    private User user;

    // For JPA
    protected GpxTrack() {
        super();
    }

    public GpxTrack(String gpxTrack, Run run, User user) {
        this.gpxTrack = gpxTrack;
        this.run = run;
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

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    @Override
    public String toString() {
        return "GpxTrack{" +
                "id=" + id +
                ", gpxTrack='" + gpxTrack + '\'' +
                ", run=" + run +
                ", user=" + user +
                '}';
    }
}
