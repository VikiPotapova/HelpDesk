package com.potapova.helpdesk.domain.dto;

import com.potapova.helpdesk.domain.Category;
import com.potapova.helpdesk.domain.Urgency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketForUpdateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate desiredResolutionDate;
    @NotBlank
    private Category category;
    @NotNull
    private Urgency urgency;
}