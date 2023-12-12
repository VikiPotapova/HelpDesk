package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketDTO;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;
import com.potapova.helpdesk.domain.dto.TicketForUpdateDTO;
import com.potapova.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ModelMapper modelMapper;

    @PostMapping("/{userId}")
    public ResponseEntity<TicketDetailsDTO> createTicket(@PathVariable Long userId, @RequestBody @Valid TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(modelMapper.map(ticketDTO, Ticket.class), userId);
        return new ResponseEntity<>(modelMapper.map(ticket, TicketDetailsDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailsDTO> getTicketById(@PathVariable Long id, @RequestParam Long userId) {
        return new ResponseEntity<>(modelMapper.map(ticketService.getTicketById(id, userId), TicketDetailsDTO.class), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<TicketDetailsDTO>> getTicketList(
            @PageableDefault(value = 5, sort = "createdOn", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Long userId) {
        return new ResponseEntity<>(ticketService.getUserTickets(pageable, userId)
                .map(ticket -> modelMapper.map(ticket, TicketDetailsDTO.class)), HttpStatus.OK);
    }

    @PatchMapping("/status/{ticketId}")
    public ResponseEntity<Void> updateTicketStatus(@PathVariable Long ticketId, @RequestParam Status status, @RequestParam Long userId) {
        ticketService.updateTicketStatus(status, ticketId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{ticketId}/users/{userId}")
    public ResponseEntity<Void> updateTicket(@PathVariable Long ticketId, @PathVariable Long userId, @RequestBody @Valid TicketForUpdateDTO ticketForUpdateDTO) {
        ticketService.updateTicketById(ticketForUpdateDTO, ticketId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}