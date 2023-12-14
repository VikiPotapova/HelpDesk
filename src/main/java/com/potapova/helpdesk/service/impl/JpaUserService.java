package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.UserNotFoundException;
import com.potapova.helpdesk.repository.UserRepository;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JpaUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getByEmail(login).orElseThrow(() ->
                new UserNotFoundException("User with login: " + login + " not found"));
    }

    @Override
    public User getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(login).orElseThrow(() ->
                new UserNotFoundException("User with login: " + login + " not found"));
    }
}