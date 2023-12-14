package com.potapova.helpdesk.security.domain;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
