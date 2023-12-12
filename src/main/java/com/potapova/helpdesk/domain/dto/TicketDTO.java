package com.potapova.helpdesk.domain.dto;

import com.potapova.helpdesk.domain.Category;
import com.potapova.helpdesk.domain.Status;
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
public class TicketDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate desiredResolutionDate;
    @NotNull
    private Category category;
    @NotNull
    private Urgency urgency;
    @NotNull
    private Status status;
}