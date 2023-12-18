package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.History;
import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.domain.dto.TicketForUpdateDTO;
import com.potapova.helpdesk.exceptionResolver.IncorrectStatusException;
import com.potapova.helpdesk.exceptionResolver.NoAccessException;
import com.potapova.helpdesk.exceptionResolver.TicketNotFoundException;
import com.potapova.helpdesk.repository.TicketRepository;
import com.potapova.helpdesk.service.HistoryService;
import com.potapova.helpdesk.service.TicketService;
import com.potapova.helpdesk.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Ticket createTicket(Ticket ticket) {
        User owner = userService.getCurrentUser();
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
    public Ticket getTicketById(Long ticketId) {
        User user = userService.getCurrentUser();
        Ticket ticket = ticketRepository.findById(ticketId).
                orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (user.equals(ticket.getOwner())) {
            return ticket;
        } else if (user.getRole().equals(Role.MANAGER) && !(ticket.getStatus().equals(Status.DRAFT))) {
            return ticket;
        } else if (ticket.getStatus().equals(Status.APPROVED) || ticket.getStatus().equals(Status.IN_PROGRESS) ||
                ticket.getStatus().equals(Status.DONE) && ticket.getAssignee().equals(user)) {
            return ticket;
        } else {
            throw new NoAccessException("User with login: " + user.getEmail() + " has no access to this information");
        }
    }

    @Override
    public Page<Ticket> getUserTickets(Pageable pageable) {
        User user = userService.getCurrentUser();
        return switch (user.getRole()) {
            case MANAGER -> ticketRepository.findApproversTickets(pageable, user.getEmail());
            case ENGINEER -> ticketRepository.findAssigneesTickets(pageable, user.getEmail());
            case EMPLOYEE -> ticketRepository.findOwnersTickets(pageable, user.getEmail());
        };
    }

    @Transactional
    @Override
    public void updateTicketStatus(Status status, Long ticketId) {
        User user = userService.getCurrentUser();
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (user.getRole().equals(Role.MANAGER) && existingTicket.getStatus().equals(Status.NEW)) {
            if (status.equals(Status.APPROVED) || status.equals(Status.DECLINED)) {
                existingTicket.setStatus(status);
                existingTicket.setApprover(user);
                ticketRepository.save(existingTicket);
                addToHistory(existingTicket, user, "The ticket status is changed", "The status was changed to " +
                        status.name());
            } else {
                throw new IncorrectStatusException("The user with login: " + user.getEmail() + " added incorrect status");
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
        } else if (existingTicket.getStatus().equals(Status.DRAFT) || existingTicket.getStatus().equals(Status.DECLINED) && (existingTicket.getOwner().equals(user))) {
            if (status.equals(Status.CANCELLED) || status.equals(Status.NEW)) {
                existingTicket.setStatus(status);
                ticketRepository.save(existingTicket);
                addToHistory(existingTicket, user, "The ticket status is changed", "The status was changed to " +
                        status.name());
            }
        } else {
            throw new NoAccessException("User with login: " + user.getEmail() +
                    " does not have access to change this information");
        }

    }

    @Transactional
    @Override
    public void updateTicketById(TicketForUpdateDTO ticketForUpdateDTO, Long ticketId) {
        User user = userService.getCurrentUser();
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (user.equals(existingTicket.getOwner()) && existingTicket.getStatus().equals(Status.DRAFT)
                || existingTicket.getStatus().equals(Status.NEW) || existingTicket.getStatus().equals(Status.DECLINED)) {
            modelMapper.map(ticketForUpdateDTO, existingTicket);
            ticketRepository.save(existingTicket);
            addToHistory(existingTicket, user, "The ticket is updated", "The ticket is updated");
        } else {
            throw new NoAccessException("User with login: " + user.getEmail() + " does not have access to this information");
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