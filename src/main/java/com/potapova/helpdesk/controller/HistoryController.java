package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.dto.HistoryDTO;
import com.potapova.helpdesk.service.HistoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;
    private final ModelMapper modelMapper;

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<HistoryDTO>> getTicketHistory(@PathVariable Long ticketId) {
        return new ResponseEntity<>(historyService.getTicketHistory(ticketId).stream()
                .map(history -> modelMapper.map(history, HistoryDTO.class)).toList(), HttpStatus.OK);
    }
}
