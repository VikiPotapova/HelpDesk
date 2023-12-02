package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.*;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;
import com.potapova.helpdesk.exceptionResolver.IncorrectStatusException;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.exceptionResolver.TicketNotFoundException;
import com.potapova.helpdesk.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class JpaTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final HistoryService historyService;

    @Transactional
    @Override
    public Ticket createTicket(Ticket ticket, Long userId) {
        User owner = userService.getUserById(userId);
        ticket.setOwner(owner);
        if (ticket.getStatus().equals(Status.NEW) || ticket.getStatus().equals(Status.DRAFT)) {
            ticketRepository.save(ticket);
        } else {
            throw new IncorrectStatusException("The ticket could not been created. Wrong Status.");
        }
        History history = History.builder()
                .ticket(ticket)
                .user(owner)
                .action("Ticket is created")
                .description("Ticket is created")
                .build();
        historyService.saveTicketToHistory(history);
        return ticket;
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + id + " not found"));
    }

    @Override
    public void updateTicketStatus(Status status, Long ticketId, Long userId) {
        User user = userService.getUserById(userId);
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (existingTicket.getOwner().getRole().equals(Role.EMPLOYEE) && user.getRole().equals(Role.MANAGER)
                && existingTicket.getStatus().equals(Status.NEW)) {
            if (status.equals(Status.APPROVED) || status.equals(Status.DECLINED) || status.equals(Status.CANCELLED)) {
                existingTicket.setStatus(status);
                existingTicket.setApprover(user);
                ticketRepository.save(existingTicket);
            } else {
                throw new IncorrectStatusException("The user with id: " + userId + " added incorrect status");
            }
        } else if (user.getRole().equals(Role.ENGINEER) && existingTicket.getStatus().equals(Status.APPROVED)) {
            if (status.equals(Status.IN_PROGRESS)) {
                existingTicket.setStatus(status);
                existingTicket.setAssignee(user);
                ticketRepository.save(existingTicket);
            }
        } else if (user.getRole().equals((Role.ENGINEER)) && existingTicket.getStatus().equals(Status.IN_PROGRESS)) {
            if (status.equals(Status.DONE)) {
                existingTicket.setStatus(status);
                ticketRepository.save(existingTicket);
            }
        } else if (existingTicket.getStatus().equals(Status.DRAFT) && (user.getRole().equals(Role.EMPLOYEE)
                || user.getRole().equals(Role.MANAGER))) {
            if (status.equals(Status.CANCELLED)) {
                existingTicket.setStatus(status);
                ticketRepository.save(existingTicket);
            }
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
