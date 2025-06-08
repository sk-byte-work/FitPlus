package com.example.fitplus;

import com.example.fitplus.security.CustomUserDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.hibernate.Session;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter
{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUserId();
            AppThreadLocals.setCurrentUserId(userId);
        }

        try
        {
            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            AppThreadLocals.clearCurrentUserId();
        }
    }
}
