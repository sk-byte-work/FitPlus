package com.example.fitplus.security;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public class CustomUserDetails implements UserDetails
{
    private final Long id;
    private final String username;
    private final String password;
    private final List authorities;

    public CustomUserDetails(Long id, String username, String password,
            List authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return id;
    }

    @Override
    public List getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
