package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findByTicketId(Pageable pageable, Long id);
}