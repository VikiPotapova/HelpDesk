package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.History;
import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;

import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket, Long userId);

    Ticket getTicketById(Long id);
    List<History> getTicketHistory(Long ticketId);
    void updateTicketById(TicketDetailsDTO ticketDTO, Long ticketId, Long userId);
    void updateTicketStatus(Status status, Long ticketId, Long userId);

}
