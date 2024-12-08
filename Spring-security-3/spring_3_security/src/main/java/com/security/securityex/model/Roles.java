package com.security.securityex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.securityex.RoleStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleStatus roles;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private UserEntity user;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public RoleStatus getRoles() {
        return roles;
    }

    public void setRoles(RoleStatus roles) {
        this.roles = roles;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
