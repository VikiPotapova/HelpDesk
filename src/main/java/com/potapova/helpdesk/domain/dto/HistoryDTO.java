package com.potapova.helpdesk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {
    private LocalDateTime date;
    private UserNameDTO user;
    private String action;
    private String description;
}
