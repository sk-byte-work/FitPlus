package com.example.fitplus.security;

import com.example.fitplus.exceptions.FitPlusException;
import com.example.fitplus.users.User;
import com.example.fitplus.users.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtil
{
    private final UserRepository userRepository;

    public AuthUtil(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public Optional<String> getCurrentUserName()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails)
        {
            UserDetails usd = (UserDetails) authentication.getPrincipal();
            return Optional.of(usd.getUsername());
        }

        return Optional.empty();
    }

    public Long getUserId()
    {
        Optional<String> currentUserName = getCurrentUserName();
        if(currentUserName.isPresent())
        {
            User currentUser = getUserRepository().findByUserName(currentUserName.get())
                    .orElseThrow(() -> new FitPlusException("User Not Found"));
            return currentUser.getId();
        }

        throw new FitPlusException("No user is logged in");
    }

    public UserRepository getUserRepository()
    {
        return userRepository;
    }
}
