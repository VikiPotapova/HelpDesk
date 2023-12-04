package com.potapova.helpdesk.domain.dto;

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
    private String name;
    private String description;
    private LocalDate desiredResolutionDate;
}
