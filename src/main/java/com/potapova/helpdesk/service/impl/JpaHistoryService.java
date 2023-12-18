package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.History;
import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.HistoryNotFoundException;
import com.potapova.helpdesk.exceptionResolver.NoAccessException;
import com.potapova.helpdesk.repository.HistoryRepository;
import com.potapova.helpdesk.service.HistoryService;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaHistoryService implements HistoryService {

    private final HistoryRepository historyRepository;
    private final UserService userService;

    @Override
    public void saveTicketToHistory(History history) {
        historyRepository.save(history);
    }

    @Override
    public Page<History> getTicketHistory(Pageable pageable, Long ticketId) {
        User user = userService.getCurrentUser();
        if (!user.getRole().equals(Role.MANAGER)) {
            throw new NoAccessException("User with login: " + user.getEmail() + " has no access to this action");
        }
        return historyRepository.findByTicketId(pageable, ticketId);
    }

    @Override
    public void deleteHistoryById(Long id) {
        User user = userService.getCurrentUser();
        History history = historyRepository.findById(id).orElseThrow(() -> new HistoryNotFoundException("History is not found"));
        if (!(history.getUser().equals(user) || user.getRole().equals(Role.MANAGER))) {
            throw new NoAccessException("The user with login: " + user.getEmail() + " has no access to delete this history");
        }
        historyRepository.deleteById(id);
    }
}