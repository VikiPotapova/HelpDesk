package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.History;
import com.potapova.helpdesk.repository.HistoryRepository;
import com.potapova.helpdesk.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaHistoryService implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public void saveTicketToHistory(History history) {
        historyRepository.save(history);
    }

    @Override
    public Page<History> getTicketHistory(Pageable pageable, Long ticketId) {
        return historyRepository.findByTicketId(pageable, ticketId);
    }
}