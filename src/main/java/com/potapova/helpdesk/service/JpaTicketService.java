package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.domain.dto.TicketDTO;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.exceptionResolver.TicketNotFoundException;
import com.potapova.helpdesk.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;


    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + id + " not found"));
    }


    @Override
    public Ticket createTicket(Ticket ticket, Long userId) {
        User owner = userService.getUserById(userId);
        ticket.setOwner(owner);
        ticket.setCreatedOn(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    @Override
    public Boolean updateTicketById(TicketDTO ticketDTO, Long ticketId, Long userId) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (userId.equals(existingTicket.getOwner().getId())) {
            ticketRepository.save(existingTicket);
            return true;
        } else {
            throw new NoAccessByIdException(userId);
        }
    }

}
