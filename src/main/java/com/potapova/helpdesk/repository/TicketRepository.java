package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("""
            SELECT t FROM Ticket t 
            WHERE t.owner.id=:id
            AND (t.status='DRAFT' OR t.status='NEW' OR t.status='APPROVED' OR t.status='IN_PROGRESS' OR t.status='DONE')
            """)
    List<Ticket> findOwnersTickets(Long id);

    @Query("""
            SELECT t FROM Ticket t 
            WHERE t.status='APPROVED'
            OR (t.assignee.id=:id AND (t.status='IN_PROGRESS' OR t.status='DONE'))
            """)
    List<Ticket> findAssigneesTickets(Long id);

    @Query("""
            SELECT t FROM Ticket t 
            WHERE t.status='NEW'
            OR (t.approver.id=:id 
            AND (t.status='APPROVED' OR t.status='DECLINED' OR t.status='IN_PROGRESS' OR t.status='DONE'))
            """)
    List<Ticket> findApproversTickets(Long id);
}