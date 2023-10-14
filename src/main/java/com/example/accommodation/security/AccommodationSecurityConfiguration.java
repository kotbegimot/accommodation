package com.example.accommodation.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

import static com.example.accommodation.util.ControllerUtils.BASE_URL;

@Configuration
@EnableWebSecurity
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
    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.GET, BASE_URL).hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, BASE_URL + "/**").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.POST, BASE_URL).hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, BASE_URL + "/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PATCH, BASE_URL + "/book/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, BASE_URL + "/**").hasRole("ADMIN")
                                .requestMatchers("/v2/api-docs/**", "/swagger-ui/**", "/swagger-resources/**",
                                        "/v2/api-docs/**", "/webjars/**" , "/swagger.json").permitAll()
                                .anyRequest().authenticated());

        // use HTTP basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable CSRF (Cross Site Request Forgery)
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
