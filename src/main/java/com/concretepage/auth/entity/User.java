package com.concretepage.auth.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", unique = true,
            nullable = false, length = 45)
    @ApiModelProperty(notes = "User's username")

    private String username;

    @Column(name = "password",
            nullable = false, length = 60)
    @ApiModelProperty(notes = "User's password")

    private String password;

    @Column(name = "enabled", nullable = false)
    @ApiModelProperty(notes = "User's enabled")

    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @ApiModelProperty(notes = "User's roles")

    private Set<UserRole> userRole = new HashSet<UserRole>(0);

    public User() {
    }

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String username, String password,
                boolean enabled, Set<UserRole> userRole) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getUserRole() {
        return this.userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", userRole=" + userRole +
                '}';
    }
}