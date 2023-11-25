package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.dto.TicketDTO;
import com.potapova.helpdesk.domain.dto.TicketDTOMapper;
import com.potapova.helpdesk.exceptionResolver.TicketNotFoundException;
import com.potapova.helpdesk.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketDTOMapper ticketDTOMapper;

    @Override
    public TicketDTO getTicketById(Long id) {
        return ticketRepository.findById(id)
                .map(ticketDTOMapper)
                .orElseThrow(TicketNotFoundException::new);
    }
}
