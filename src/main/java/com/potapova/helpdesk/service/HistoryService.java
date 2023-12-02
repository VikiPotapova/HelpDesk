package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.History;

import java.util.List;

public interface HistoryService {
    void saveTicketToHistory(History history);

    List<History> getTicketHistory(Long ticketId);
}
