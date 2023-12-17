package com.potapova.helpdesk.security;

import com.potapova.helpdesk.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(new AntPathRequestMatcher("/tickets", "POST")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/tickets", "GET")).hasAnyRole(
                                        "EMPLOYEE", "MANAGER", "ENGINEER")
                                .requestMatchers(new AntPathRequestMatcher("/tickets/status", "PATCH")).hasAnyRole(
                                        "EMPLOYEE", "MANAGER", "ENGINEER"
                                )
                                .requestMatchers(new AntPathRequestMatcher("/tickets", "PUT")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/users", "GET")).hasRole("MANAGER")
                                .requestMatchers(new AntPathRequestMatcher("/users", "DELETE")).hasRole("MANAGER")

                                .requestMatchers(new AntPathRequestMatcher("/histories/tickets", "GET")).hasRole("MANAGER")

                                .requestMatchers(new AntPathRequestMatcher("/comments/tickets", "POST")).hasAnyRole(
                                        "EMPLOYEE", "MANAGER", "ENGINEER"
                                )
                                .requestMatchers(new AntPathRequestMatcher("/comments/tickets", "GET")).hasAnyRole(
                                        "EMPLOYEE", "MANAGER", "ENGINEER"
                                )

                                .requestMatchers(new AntPathRequestMatcher("/feedbacks/tickets", "POST")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/feedbacks", "GET")).hasAnyRole(
                                        "EMPLOYEE", "MANAGER", "ENGINEER"
                                )
                                .requestMatchers(new AntPathRequestMatcher("/feedbacks/users", "GET")).hasRole("MANAGER")

                                .requestMatchers(new AntPathRequestMatcher("/security/registration", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/security", "POST")).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

