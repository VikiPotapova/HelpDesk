package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.History;
import com.potapova.helpdesk.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaHistoryService implements HistoryService {
    private final HistoryRepository historyRepository;

    @Override
    public void saveTicketToHistory(History history) {
        historyRepository.save(history);
    }

    @Override
    public List<History> getTicketHistory(Long ticketId) {
        return historyRepository.findByTicketId(ticketId);
    }

}