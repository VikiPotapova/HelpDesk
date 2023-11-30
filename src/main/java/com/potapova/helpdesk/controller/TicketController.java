package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.Status;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.dto.HistoryDTO;
import com.potapova.helpdesk.domain.dto.TicketDTO;
import com.potapova.helpdesk.domain.dto.TicketDetailsDTO;
import com.potapova.helpdesk.service.JpaTicketService;
import com.potapova.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final ModelMapper modelMapper;

    @PostMapping("/{userId}")
    public ResponseEntity<TicketDetailsDTO> createTicket(@PathVariable Long userId, @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(modelMapper.map(ticketDTO, Ticket.class), userId);
        return new ResponseEntity<>(modelMapper.map(ticket, TicketDetailsDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailsDTO> getTicketById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(modelMapper.map(ticketService.getTicketById(id), TicketDetailsDTO.class), HttpStatus.OK);
    }

    @GetMapping("/history/{ticketId}")
    public ResponseEntity<List<HistoryDTO>> getTicketHistory(@PathVariable Long ticketId) {
        return new ResponseEntity<>(ticketService.getTicketHistory(ticketId).stream()
                .map(history -> modelMapper.map(history, HistoryDTO.class)).toList(), HttpStatus.OK);
    }


    @PatchMapping("/{status}/{ticketId}/{userId}")
    public ResponseEntity<Void> updateTicket(@PathVariable Status status,@PathVariable Long ticketId, @PathVariable Long userId) {
        ticketService.updateTicketStatus(status, ticketId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{ticketId}/users/{userId}")
    public ResponseEntity<Void> updateTicket(@PathVariable Long ticketId, @PathVariable Long userId, @RequestBody TicketDetailsDTO ticketDTO) {
        ticketService.updateTicketById(ticketDTO, ticketId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
