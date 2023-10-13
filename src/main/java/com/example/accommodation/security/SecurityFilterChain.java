package com.example.accommodation.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;

import static com.example.accommodation.util.ControllerUtils.BASE_URL;

public class SecurityFilterChain
{
    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, BASE_URL).hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, BASE_URL + "/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, BASE_URL).hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, BASE_URL + "/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, BASE_URL + "/book/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, BASE_URL + "/**").hasRole("ADMIN"));

        // use HTTP basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable CSRF (Cross Site Request Forgery)
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
