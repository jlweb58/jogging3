package com.webber.jogging.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webber.jogging.activity.Activity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    private static final long serialVersionUID = 3630469923395227112L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Gear> gear = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();

    public Long getId() {
        return id;
    }

    protected User() {
        //Needed for Hibernate
    }

    public User(String username, String password, String email, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<Gear> getGear() {
        return gear;
    }

    public void setGear(List<Gear> gear) {
        this.gear = gear;
    }

    public void addGear(Gear newGear) {
        if (!gear.contains(newGear)) {
            gear.add(newGear);
            newGear.setUser(this);
        }
    }

    public void addActivity(Activity newActivity) {
        activities.add(newActivity);
        newActivity.setUser(this);
    }

    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "User [id=" + getId() + ", username=" + username + ", password= [PROTECTED], email=" + email + ",  enabled=" + enabled + "]";
    }



}
