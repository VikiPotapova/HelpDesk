package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.dto.TicketDTO;

public interface  TicketService {
    TicketDTO getTicketById(Long id);
}
