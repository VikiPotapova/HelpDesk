package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.service.AccessService;
import com.potapova.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final TicketService ticketService;

    @Override
    public Boolean isUserBelongToTicket(User user, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return user.equals(ticket.getOwner()) || user.equals(ticket.getApprover()) || user.equals(ticket.getAssignee());
    }
}