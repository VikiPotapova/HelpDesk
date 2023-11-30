package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;

public interface TicketService {
    Ticket getTicketById(Long id);

    Ticket createTicket(Ticket ticket, Long userId);

    void updateTicketById(TicketDetailsDTO ticketDTO, Long ticketId, Long userId);
}
