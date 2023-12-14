package com.potapova.helpdesk.security.controller;

import com.potapova.helpdesk.security.domain.AuthRequest;
import com.potapova.helpdesk.security.domain.AuthResponse;
import com.potapova.helpdesk.security.domain.RegistrationDTO;
import com.potapova.helpdesk.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationDTO registrationDTO) {
        securityService.registration(registrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        Optional<String> token = securityService.generateToken(authRequest);
        if (token.isPresent()) {
            return new ResponseEntity<>(new AuthResponse(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<AuthResponse>(HttpStatus.UNAUTHORIZED);
    }
}
