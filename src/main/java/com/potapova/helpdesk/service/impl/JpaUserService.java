package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.NoAccessException;
import com.potapova.helpdesk.exceptionResolver.UserNotFoundException;
import com.potapova.helpdesk.repository.UserRepository;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    @Override
    public void deleteUserById(Long id) {
        User user = getCurrentUser();
        if (!user.getRole().equals(Role.MANAGER)) {
           throw new NoAccessException("User with login: " + user.getEmail() + " has no access to this action");
        }
        userRepository.deleteById(id);
    }
}