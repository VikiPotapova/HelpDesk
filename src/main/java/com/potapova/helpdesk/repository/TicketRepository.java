package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("select t from Ticket t where t.owner.id=:id or t.assignee.id=:id or t.approver=:id")
    List<Ticket> findByUserId(Long id);
}
