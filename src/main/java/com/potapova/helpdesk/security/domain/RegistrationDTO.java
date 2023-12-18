package com.potapova.helpdesk.security.domain;

import com.potapova.helpdesk.domain.Role;
import lombok.Data;

@Data
public class RegistrationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}