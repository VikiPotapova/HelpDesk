package com.potapova.helpdesk.security.service;

import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.UserNotFoundException;
import com.potapova.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDataBase = userRepository.getByLogin(username);
        if (Objects.isNull(userFromDataBase)) {
            throw new UserNotFoundException("User with login: " + username + " is not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(userFromDataBase.getEmail())
                .password(userFromDataBase.getPassword())
                .roles(userFromDataBase.getRole().toString())
                //.authorities(userFromDataBase.getRole().name())
                .build();
    }
}

