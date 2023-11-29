package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketDTO;

public interface TicketService {
    Ticket getTicketById(Long id);

    Ticket createTicket(Ticket ticket, Long userId);

    Boolean updateTicketById(TicketDTO ticketDTO, Long ticketId, Long userId);
}
