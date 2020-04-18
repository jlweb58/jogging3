package com.webber.jogging.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole extends AbstractPersistable<Long> {

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name="rolename", nullable = false)
    private Role role;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    private UserRole() {
        // for hibernate
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
