package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Entity(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "seq_user", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @Column(name = "id")
    private Long id;

    @Size(min = 2, max = 20)
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    private Role role;

    @Size(max = 100)
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    @Column(name = "email")
    private String email;

    @Size(min = 6, max = 20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).$")
    @Column(name = "password")
    private String password;
}
