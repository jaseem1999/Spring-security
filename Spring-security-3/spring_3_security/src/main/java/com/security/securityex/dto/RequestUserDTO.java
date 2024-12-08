package com.security.securityex.dto;

import com.security.securityex.model.Roles;

import java.util.Set;

public class RequestUserDTO {
    private String email;
    private String password;

    private Set<Roles> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
