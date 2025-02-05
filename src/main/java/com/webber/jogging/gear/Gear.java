package com.webber.jogging.gear;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.webber.jogging.user.User;
import com.webber.jogging.user.UserResource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Gear implements UserResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // The mileage offset is a user-configurable base value for the mileage, e.g.
    // if "used" gear is added, where the user knows approximately how many km
    // the gear already has.
    @Column(name = "mileage_offset")
    private double mileageOffset;

    // This is calculated dynamically on loading
    // TODO maybe that's not the best idea
    @Transient
    private double mileage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Inactive gear won't appear in the combo box for new activity / edit activity
    @Column(name = "active", nullable = false)
    private boolean active;

    // Whether this gear is the current default (not allowed to call the
    // field "default"
    @Column(name = "preferred", nullable = false)
    private boolean preferred;

    @Enumerated(EnumType.STRING)
    @Column(name = "gear_type")
    private GearType gearType;

    public Gear(String name, double mileageOffset, User user) {
        this.name = name;
        this.mileageOffset = mileageOffset;
        this.user = user;
        this.active = true;
    }

    protected Gear() {

        //for JPA
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Gear{" + "id=" + getId() + ", name=" + name + ", mileage=" + mileage + ", mileageOffset=" + mileageOffset + ", user=" + user
                + ", active=" + active + ", gearType=" + gearType + ", preferred=" + preferred + '}';
    }
}
