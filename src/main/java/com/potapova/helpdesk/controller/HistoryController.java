package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.dto.HistoryDTO;
import com.potapova.helpdesk.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryService historyService;
    private final ModelMapper modelMapper;

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<Page<HistoryDTO>> getTicketHistory(
            @PageableDefault(value = 5, sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long ticketId) {
        return new ResponseEntity<>(historyService.getTicketHistory(pageable, ticketId)
                .map(history -> modelMapper.map(history, HistoryDTO.class)), HttpStatus.OK);
    }
}