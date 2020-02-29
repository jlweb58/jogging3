package com.webber.jogging.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 3630469923395227112L;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(targetEntity = Run.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Run> runs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Shoes> shoes = new ArrayList<>();

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

    //TODO Make sure this isn't serialized
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

    //TODO make sure these are not loaded
    public List<Run> getRuns() {
        return runs;
    }

    public List<Shoes> getShoes() {
        return shoes;
    }

    public void setShoes(List<Shoes> shoes) {
        this.shoes = shoes;
    }

    public void addShoes(Shoes newShoes) {
        if (!shoes.contains(newShoes)) {
            shoes.add(newShoes);
        }
    }

    @Override
    public String toString() {
        return "User [id=" + getId() + ", username=" + username + ", password= [PROTECTED], email=" + email + ",  enabled=" + enabled + "]";
    }



}
