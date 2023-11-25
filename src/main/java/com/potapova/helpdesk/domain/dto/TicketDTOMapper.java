package com.potapova.helpdesk.domain.dto;

import com.potapova.helpdesk.domain.Ticket;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TicketDTOMapper implements Function<Ticket, TicketDTO> {
    @Override
    public TicketDTO apply(Ticket ticket) {
        return new TicketDTO(
                ticket.getName(),
                ticket.getDescription(),
                ticket.getCreatedOn(),
                ticket.getDesiredResolutionDate(),
                ticket.getAssignee(),
                ticket.getOwner(),
                ticket.getApproved(),
                ticket.getStatus(),
                ticket.getCategory(),
                ticket.getUrgency()
        );
    }
}
