package com.example.accommodation.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

import javax.sql.DataSource;

import static com.example.accommodation.util.ControllerUtils.BASE_URL;

@Configuration()
@EnableWebSecurity
public class AccommodationSecurityConfiguration {
    @Bean
    @ConditionalOnProperty(name = "security.users.in-memory", havingValue = "true")
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        // Default passwords here are: test123
        UserDetails devon = User.builder()
                .username("devon")
                .password("{bcrypt}$2a$10$goLOWsblvk3LycfYKnwQYejFliUNQkf9KNG8K0mAer0M2VNL3FxHm")
                .roles("CUSTOMER")
                .build();
        UserDetails kirk = User.builder()
                .username("kirk")
                .password("{bcrypt}$2a$10$BfxYPKRUgVgkKWOyNNJfk.IJvIq7xnWWUmdYot1fcCHl8Jt65QYP2")
                .roles("CUSTOMER", "MANAGER")
                .build();
        UserDetails nagibator = User.builder()
                .username("nagibator")
                .password("{bcrypt}$2a$10$wQ.T2H4YpGCOT4tX8eR0n.x2SNZycFD.tu33Fxwf.3Mhid.WQ2EJO")
                .roles("CUSTOMER", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(devon, kirk, nagibator);
    }

    @Bean
    @ConditionalOnProperty(name = "security.users.in-memory", havingValue = "false")
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // how to find users
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pwd, active from members where user_id=?"
        );
        // how to find roles
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?"
        );
        return jdbcUserDetailsManager;
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
                                "/v2/api-docs/**", "/webjars/**", "/swagger.json").permitAll()
                        .anyRequest().authenticated());

        // use HTTP basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable CSRF (Cross Site Request Forgery)
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
