package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.TicketDTO;
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
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(modelMapper.map(ticketService.getTicketById(id), TicketDTO.class), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<TicketDTO> createTicket(@PathVariable Long userId, @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(modelMapper.map(ticketDTO, Ticket.class), userId);
        return new ResponseEntity<>(modelMapper.map(ticket, TicketDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("/{ticketId}/users/{userId}")
    public ResponseEntity<HttpStatus> updateTicket(@PathVariable Long ticketId, @PathVariable Long userId, @RequestBody TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketService.updateTicketById(ticketDTO, ticketId, userId)
                ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

}
