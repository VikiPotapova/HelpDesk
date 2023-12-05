package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByApproverId(Long id);

    List<Ticket> findByOwnerId(Long ownerId);

    List<Ticket> findByAssigneeId(Long id);
}
