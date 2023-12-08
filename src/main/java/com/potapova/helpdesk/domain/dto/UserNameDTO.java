package com.potapova.helpdesk.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNameDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}