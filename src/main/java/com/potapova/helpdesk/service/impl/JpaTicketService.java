package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.*;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userService.getUserByLogin(userLogin);
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
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
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
            throw new NoAccessException("User with login: " + userLogin + " has no access to this information");
        }
    }

    @Override
    public Page<Ticket> getUserTickets(Pageable pageable) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
        return switch (user.getRole()) {
            case MANAGER -> ticketRepository.findApproversTickets(pageable, userLogin);
            case ENGINEER -> ticketRepository.findAssigneesTickets(pageable, userLogin);
            case EMPLOYEE -> ticketRepository.findOwnersTickets(pageable, userLogin);
        };
    }

    @Transactional
    @Override
    public void updateTicketStatus(Status status, Long ticketId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
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
                throw new IncorrectStatusException("The user with login: " + userLogin + " added incorrect status");
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
            throw new NoAccessException("User with login: " + userLogin +
                    " does not have access to change this information");
        }

    }

    @Transactional
    @Override
    public void updateTicketById(TicketForUpdateDTO ticketForUpdateDTO, Long ticketId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userEmail);
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id: " + ticketId + " not found"));
        if (user.equals(existingTicket.getOwner()) && existingTicket.getStatus().equals(Status.DRAFT)
                || existingTicket.getStatus().equals(Status.NEW) || existingTicket.getStatus().equals(Status.DECLINED)) {
            modelMapper.map(ticketForUpdateDTO, existingTicket);
            ticketRepository.save(existingTicket);
            addToHistory(existingTicket, user, "The ticket is updated", "The ticket is updated");
        } else {
            throw new NoAccessException("User with login: " + userEmail + " does not have access to this information");
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