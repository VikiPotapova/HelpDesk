package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2, max = 20)
    private String firstName;

    @Size(min = 2, max = 50)
    private String lastName;

    private Role role;

    @Size(max = 100)
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    private String email;

    @Size(min = 6, max = 20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).$")
    private String password;
}
