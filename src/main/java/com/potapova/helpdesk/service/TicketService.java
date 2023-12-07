package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketForUpdateDTO;

import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket, Long userId);

    Ticket getTicketById(Long id, Long userId);

    List<Ticket> getUserTickets(Long userId);

    void updateTicketById(TicketForUpdateDTO ticketForUpdateDTO, Long ticketId, Long userId);

    void updateTicketStatus(Status status, Long ticketId, Long userId);

}