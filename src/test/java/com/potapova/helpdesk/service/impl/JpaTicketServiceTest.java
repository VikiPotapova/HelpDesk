package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.Category;
import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.Urgency;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.repository.TicketRepository;
import com.potapova.helpdesk.service.HistoryService;
import com.potapova.helpdesk.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class JpaTicketServiceTest {
    @Mock
    TicketRepository ticketRepository;
    @Mock
    UserService userService;
    @Mock
    HistoryService historyService;
    @InjectMocks
    JpaTicketService jpaTicketService;

    private Ticket ticket;
    private User userOwnerForTest;
    private Pageable pageable;
    private Page<Ticket> ticketPage;

    @BeforeEach
    void before() {
        userOwnerForTest = User.builder()
                .id(10L)
                .firstName("FirstNameOwner")
                .lastName("LastNameOwner")
                .email("ownerEmail")
                .password("password")
                .role(Role.EMPLOYEE)
                .build();

        List<Ticket> ticketList = new ArrayList<>();
        ticket = Ticket.builder()
                .id(10L)
                .name("Name")
                .description("Description")
                .desiredResolutionDate(LocalDate.ofEpochDay(2023 - 12 - 20))
                .owner(userOwnerForTest)
                .urgency(Urgency.CRITICAL)
                .status(Status.NEW)
                .category(Category.APPLICATION_AND_SERVICES)
                .build();

        ticketList.add(ticket);
        pageable = Pageable.ofSize(5);
        ticketPage = new PageImpl<>(ticketList);
    }

    @Test
    void createTicket() {
        Mockito.when(ticketRepository.save(any())).thenReturn(ticket);
        Ticket resultTicket = jpaTicketService.createTicket(ticket);
        Mockito.verify(ticketRepository, Mockito.times(1)).save(any());
        Assertions.assertNotNull(resultTicket);
    }

    @Test
    void getTicketById() {
        Mockito.when(userService.getCurrentUser()).thenReturn(userOwnerForTest);
        Mockito.when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        Ticket resultTicket = jpaTicketService.getTicketById(ticket.getId());
        Mockito.verify(ticketRepository, Mockito.times(1)).findById(anyLong());
        Assertions.assertNotNull(resultTicket);
    }

    @Test
    void getUserTickets() {
        Mockito.when(userService.getCurrentUser()).thenReturn(userOwnerForTest);
        Mockito.when(ticketRepository.findOwnersTickets(pageable, ticket.getOwner().getEmail()))
                .thenReturn(ticketPage);
        Page<Ticket> resultTicketPage = jpaTicketService.getUserTickets(pageable);
        Mockito.verify(ticketRepository, Mockito.times(1)).findOwnersTickets(pageable, ticket.getOwner().getEmail());
        Assertions.assertNotNull(resultTicketPage);

    }

    @Test
    void updateTicketStatus() {
    }

    @Test
    void updateTicketById() {
    }
}