package com.example.fitplus.security;

import com.example.fitplus.users.User;
import com.example.fitplus.users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService
{

    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository()
    {
        return userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = getUserRepository().findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found :("));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .build();
    }
}
