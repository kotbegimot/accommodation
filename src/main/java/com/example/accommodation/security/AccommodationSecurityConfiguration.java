package com.example.accommodation.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AccommodationSecurityConfiguration {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails devon = User.builder()
                .username("devon")
                .password("{noop}test123")
                .roles("CUSTOMER")
                .build();
        UserDetails kirk = User.builder()
                .username("kirk")
                .password("{noop}test123")
                .roles("CUSTOMER", "MANAGER")
                .build();
        UserDetails nagibator = User.builder()
                .username("nagibator")
                .password("{noop}test123")
                .roles("CUSTOMER", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(devon, kirk, nagibator);
    }


}
