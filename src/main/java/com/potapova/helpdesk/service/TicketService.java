package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketForUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    Ticket createTicket(Ticket ticket);

    Ticket getTicketById(Long id);

    Page<Ticket> getUserTickets(Pageable pageable);

    void updateTicketById(TicketForUpdateDTO ticketForUpdateDTO, Long ticketId);

    void updateTicketStatus(Status status, Long ticketId);
}