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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<TicketDetailsDTO> createTicket(@RequestBody @Valid TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(modelMapper.map(ticketDTO, Ticket.class));
        return new ResponseEntity<>(modelMapper.map(ticket, TicketDetailsDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailsDTO> getTicketById(@PathVariable Long id) {
        return new ResponseEntity<>(modelMapper.map(ticketService.getTicketById(id), TicketDetailsDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<TicketDetailsDTO>> getTicketList(
            @PageableDefault(value = 5, sort = "createdOn", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(ticketService.getUserTickets(pageable)
                .map(ticket -> modelMapper.map(ticket, TicketDetailsDTO.class)), HttpStatus.OK);
    }

    @PatchMapping("/status/{ticketId}")
    public ResponseEntity<HttpStatus> updateTicketStatus(@PathVariable Long ticketId, @RequestParam Status status) {
        ticketService.updateTicketStatus(status, ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<HttpStatus> updateTicket(@PathVariable Long ticketId,
                                                   @RequestBody TicketForUpdateDTO ticketForUpdateDTO) {
        ticketService.updateTicketById(ticketForUpdateDTO, ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}