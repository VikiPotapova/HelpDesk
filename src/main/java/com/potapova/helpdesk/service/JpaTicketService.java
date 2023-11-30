package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.exceptionResolver.TicketNotFoundException;
import com.potapova.helpdesk.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + id + " not found"));
    }


    @Override
    public Ticket createTicket(Ticket ticket, Long userId) {
        User owner = userService.getUserById(userId);
        ticket.setOwner(owner);
        return ticketRepository.save(ticket);
    }

    @Override
    public void updateTicketById(TicketDetailsDTO ticketDTO, Long ticketId, Long userId) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (userId.equals(existingTicket.getOwner().getId())) {
            modelMapper.map(ticketDTO,existingTicket);
            ticketRepository.save(existingTicket);
        } else {
            throw new NoAccessByIdException("User with id: " + userId + " does not have access to this information");
        }

    }

}
