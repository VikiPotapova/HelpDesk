package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketForUpdateDTO;

public interface TicketService {
    Ticket createTicket(Ticket ticket, Long userId);

    Ticket getTicketById(Long id);

    void updateTicketById(TicketForUpdateDTO ticketForUpdateDTO, Long ticketId, Long userId);
    void updateTicketStatus(Status status, Long ticketId, Long userId);

}
