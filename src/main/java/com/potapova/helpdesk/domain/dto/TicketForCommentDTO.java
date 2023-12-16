package com.potapova.helpdesk.domain.dto;

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
public class TicketForCommentDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate desiredResolutionDate;
}