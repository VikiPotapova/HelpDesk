package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryService {
    void saveTicketToHistory(History history);

    Page<History> getTicketHistory(Pageable pageable, Long ticketId);

    void deleteHistoryById(Long id);
}