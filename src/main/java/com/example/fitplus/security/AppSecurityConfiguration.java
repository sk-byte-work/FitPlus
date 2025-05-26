package com.example.fitplus.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfiguration
{
    private static final Logger logger = LoggerFactory.getLogger(AppSecurityConfiguration.class);
    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity httpSecurity) throws Exception
    {
        logger.info("Initialising Custom App security configurations.");
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .permitAll()
                        .defaultSuccessUrl("/api/workouts", true))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/api/users/register").permitAll()
                        .anyRequest().authenticated());

        logger.info("Configured Custom App security configurations.");

        return httpSecurity.build();
    }


    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
