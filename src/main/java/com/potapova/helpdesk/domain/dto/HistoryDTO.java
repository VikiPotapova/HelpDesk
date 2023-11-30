package com.potapova.helpdesk.domain.dto;

import com.potapova.helpdesk.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {
    private LocalDateTime date;
    private String action;
    private User user;
    //private TicketDTO ticketDTO;
}
