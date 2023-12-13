package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.UserNotFoundException;
import com.potapova.helpdesk.repository.UserRepository;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        User user = userRepository.getByEmail(login);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User with email: " + login + " not found");
        }
        return user;
    }
}