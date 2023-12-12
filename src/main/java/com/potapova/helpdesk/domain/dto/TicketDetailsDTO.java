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
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDetailsDTO {

        private String name;
        private String description;
        private LocalDateTime createdOn;
        private LocalDate desiredResolutionDate;
        private UserDTO assignee;
        private UserDTO owner;
        private UserDTO approver;
        private Status status;
        private Category category;
        private Urgency urgency;
}