package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.*;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.exceptionResolver.TicketNotFoundException;
import com.potapova.helpdesk.repository.HistoryRepository;
import com.potapova.helpdesk.repository.TicketRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class JpaTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final HistoryRepository historyRepository;

    //transactional?
    @Override
    public Ticket createTicket(Ticket ticket, Long userId) {
        User owner = userService.getUserById(userId);
        ticket.setOwner(owner);
        ticket.setStatus(Status.NEW);
        ticketRepository.save(ticket);
        History history = History.builder()
                .ticket(ticket)
                .user(owner)
                .action("Ticket is created")
                .build();
        historyRepository.save(history);
        return ticket;
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + id + " not found"));
    }

    @Override
    public List<History> getTicketHistory(Long ticketId) {
        return historyRepository.findByTicketId(ticketId);
    }

    @Override //Не работает, ошибка нот фаунд
    public void updateTicketStatus(Status status, Long ticketId, Long userId) {
        Role userRole = userService.getUserById(userId).getRole();
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (existingTicket.getOwner().getRole().equals(Role.EMPLOYEE) &&
                (userRole.equals(Role.MANAGER)) && status.equals(Status.APPROVED)
                || status.equals(Status.DECLINED) || status.equals(Status.CANCELLED)) {
            ticketRepository.save(existingTicket);
        } else {
            throw new NoAccessByIdException("User with id: " + userId + " does not have access to change this information");
        }

    }

    @Override
    public void updateTicketById(TicketDetailsDTO ticketDetailsDTO, Long ticketId, Long userId) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (userId.equals(existingTicket.getOwner().getId())) {
            modelMapper.map(ticketDetailsDTO, existingTicket);
            ticketRepository.save(existingTicket);
        } else {
            throw new NoAccessByIdException("User with id: " + userId + " does not have access to this information");
        }


    }

}
