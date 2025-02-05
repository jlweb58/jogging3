package com.webber.jogging.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webber.jogging.activity.Activity;
import com.webber.jogging.gear.Gear;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "app_users")
public class User {

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

    protected User() {
        //Needed for Hibernate
    }

    public User(String username, String password, String email, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
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

    @Override
    public String toString() {
        return "User [id=" + getId() + ", username=" + username + ", password= [PROTECTED], email=" + email + ",  enabled=" + enabled + "]";
    }



}
