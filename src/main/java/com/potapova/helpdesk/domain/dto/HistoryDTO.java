package com.potapova.helpdesk.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {
    @CreationTimestamp
    private LocalDateTime date;
    @NotNull
    private UserNameDTO user;
    @NotBlank
    private String action;
    @NotBlank
    private String description;
}