package com.potapova.helpdesk.domain.dto;

import com.potapova.helpdesk.domain.Category;
import com.potapova.helpdesk.domain.Urgency;
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
    private String name;
    private String description;
    private LocalDate desiredResolutionDate;
    private Category category;
    private Urgency urgency;
}
