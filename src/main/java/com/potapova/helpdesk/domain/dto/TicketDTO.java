package com.potapova.helpdesk.domain.dto;

import com.potapova.helpdesk.domain.Category;
import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Urgency;
import com.potapova.helpdesk.domain.User;
import java.time.LocalDate;

public record TicketDTO(
        String name,
        String description,
        LocalDate createdOn,
        LocalDate desiredResolutionDate,
        User assignee,
        User owner,
        User approved,
        Status status,
        Category category,
        Urgency urgency
) {
}
