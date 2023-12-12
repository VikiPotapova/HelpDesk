package com.potapova.helpdesk.domain.dto;

import com.potapova.helpdesk.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Role role;
}