package com.example.damnbreadback.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private String userId;
    private String role;

    // Constructor
    public UserPrincipal(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    // Getter methods for userId and role

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    // Implement the UserDetails interface methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's role as a GrantedAuthority
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        // Assuming you don't store passwords in the JWT token, return null
        return null;
    }

    @Override
    public String getUsername() {
        // Return the user ID as the username
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Assuming there is no account expiration logic, return true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Assuming there is no account locking logic, return true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Assuming there are no credentials expiration logic, return true
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Assuming the user is always enabled, return true
        return true;
    }
}