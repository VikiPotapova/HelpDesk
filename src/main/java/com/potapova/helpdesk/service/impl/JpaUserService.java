package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.TicketNotFoundException;
import com.potapova.helpdesk.repository.UserRepository;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JpaUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("User with id: " + id + " not found"));
    }
}