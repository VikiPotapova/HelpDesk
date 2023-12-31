package com.potapova.helpdesk.security.service;

import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.SameUserInDatabaseException;
import com.potapova.helpdesk.exceptionResolver.UserNotFoundException;
import com.potapova.helpdesk.repository.UserRepository;
import com.potapova.helpdesk.security.domain.AuthRequest;
import com.potapova.helpdesk.security.domain.RegistrationDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Optional<String> generateToken(AuthRequest authRequest) {
        User user = userRepository.getByEmail(authRequest.getEmail()).orElseThrow(() ->
                new UserNotFoundException("User is not found"));
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return Optional.of(jwtUtils.generateJwtToken(authRequest.getEmail()));
        }
        return Optional.empty();
    }

    @Transactional
    public void registration(RegistrationDTO registrationDTO) {
        if (userRepository.getByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new SameUserInDatabaseException("The user already exists");
        }
        User newUser = User.builder()
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .role(Role.EMPLOYEE)
                .email(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .build();
        userRepository.save(newUser);
    }
}