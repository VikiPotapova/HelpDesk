package com.potapova.helpdesk.security.service;

import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDataBase = userService.getUserByLogin(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(userFromDataBase.getEmail())
                .password(userFromDataBase.getPassword())
                .roles(userFromDataBase.getRole().toString())
                .build();
    }
}

