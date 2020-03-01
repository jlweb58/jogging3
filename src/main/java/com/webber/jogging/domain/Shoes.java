package com.webber.jogging.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
public class Shoes extends AbstractPersistable<Long> implements UserResource {

    private static final long serialVersionUID = 2095849911820717778L;

    @Column(name = "name", nullable = false)
    private String name;

    // The mileage offset is a user-configurable base value for the mileage, e.g.
    // if "used" shoes are added, where the user knows approximately how many km
    // the shoes already have.
    @Column(name = "mileageoffset", nullable = true)
    private double mileageOffset;

    // This is calculated dynamically on loading
    // TODO maybe that's not the best idea
    @Transient
    private double mileage;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnore
    private User user;

    // An inactive shoe won't appear in the combo box for new run / edit run
    @Column(name = "active", nullable = false)
    private boolean active;

    // Whether these shoes are the current default pair (not allowed to call the
    // field "default"
    @Column(name = "preferred", nullable = false)
    private boolean preferred;

    public Shoes(String name, double mileageOffset, User user) {
        this.name = name;
        this.mileageOffset = mileageOffset;
        this.user = user;
        this.active = true;
    }

    public Shoes(String name, User user) {
        this(name, 0.0, user);
    }

    protected Shoes() {

        //for JPA
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMileageOffset() {
        return mileageOffset;
    }

    public void setMileageOffset(double mileageOffset) {
        this.mileageOffset = mileageOffset;
    }

    @Override
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    @Override
    public String toString() {
        return "Shoes{" + "id=" + getId() + ", name=" + name + ", mileage=" + mileage + ", mileageOffset=" + mileageOffset + ", user=" + user
                + ", active=" + active + ", preferred=" + preferred + '}';
    }



}
