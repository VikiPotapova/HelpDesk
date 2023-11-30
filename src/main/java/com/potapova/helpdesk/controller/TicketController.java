package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketDTO;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;
import com.potapova.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailsDTO> getTicketById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(modelMapper.map(ticketService.getTicketById(id), TicketDetailsDTO.class), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<TicketDetailsDTO> createTicket(@PathVariable Long userId, @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(modelMapper.map(ticketDTO, Ticket.class), userId);
        return new ResponseEntity<>(modelMapper.map(ticket, TicketDetailsDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("/{ticketId}/users/{userId}")
    public ResponseEntity<Void> updateTicket(@PathVariable Long ticketId, @PathVariable Long userId, @RequestBody TicketDetailsDTO ticketDTO) {
        ticketService.updateTicketById(ticketDTO, ticketId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
