package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.*;
import com.potapova.helpdesk.domain.dto.TicketForUpdateDTO;
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
        addToHistory(ticket, owner, "Ticket is created", "Ticket is created");
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
            if (status.equals(Status.APPROVED) || status.equals(Status.DECLINED)) {
                existingTicket.setStatus(status);
                existingTicket.setApprover(user);
                ticketRepository.save(existingTicket);
                addToHistory(existingTicket, user, "The ticket status is changed", "The status was changed to " +
                        status.name());
            } else {
                throw new IncorrectStatusException("The user with id: " + userId + " added incorrect status");
            }
        } else if (user.getRole().equals(Role.ENGINEER) && existingTicket.getStatus().equals(Status.APPROVED)) {
            if (status.equals(Status.IN_PROGRESS)) {
                existingTicket.setStatus(status);
                existingTicket.setAssignee(user);
                ticketRepository.save(existingTicket);
                addToHistory(existingTicket, user, "The ticket status is changed", "The status was changed to " +
                        status.name());
            }
        } else if (user.getRole().equals((Role.ENGINEER)) && existingTicket.getStatus().equals(Status.IN_PROGRESS)) {
            if (status.equals(Status.DONE)) {
                existingTicket.setStatus(status);
                ticketRepository.save(existingTicket);
                addToHistory(existingTicket, user, "The ticket status is changed", "The status was changed to " +
                        status.name());
            }
        } else if (existingTicket.getStatus().equals(Status.DRAFT) && (existingTicket.getOwner().equals(user))) {
            if (status.equals(Status.CANCELLED)) {
                existingTicket.setStatus(status);
                ticketRepository.save(existingTicket);
                addToHistory(existingTicket, user, "The ticket status is changed", "The status was changed to " +
                        status.name());
            }
        } else {
            throw new NoAccessByIdException("User with id: " + userId +
                    " does not have access to change this information");
        }

    }

    @Override
    public void updateTicketById(TicketForUpdateDTO ticketForUpdateDTO, Long ticketId, Long userId) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        User user = userService.getUserById(userId);
        if (user.equals(existingTicket.getOwner()) && existingTicket.getStatus().equals(Status.DRAFT)
                || existingTicket.getStatus().equals(Status.NEW) || existingTicket.getStatus().equals(Status.DECLINED)) {
            modelMapper.map(ticketForUpdateDTO, existingTicket);
            ticketRepository.save(existingTicket);
            addToHistory(existingTicket, user, "The ticket is updated", "The ticket is updated");
        } else {
            throw new NoAccessByIdException("User with id: " + userId + " does not have access to this information");
        }
    }

    private void addToHistory(Ticket ticket, User user, String action, String description) {
        History history = History.builder()
                .ticket(ticket)
                .user(user)
                .action(action)
                .description(description)
                .build();
        historyService.saveTicketToHistory(history);
    }

}

