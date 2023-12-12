package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("""
            SELECT t FROM Ticket t 
            WHERE t.owner.id=:id
            AND (t.status='DRAFT' OR t.status='NEW' OR t.status='APPROVED' OR t.status='IN_PROGRESS' OR t.status='DONE')
            """)
    Page<Ticket> findOwnersTickets(Pageable pageable, Long id);

    @Query("""
            SELECT t FROM Ticket t 
            WHERE t.status='APPROVED'
            OR (t.assignee.id=:id AND (t.status='IN_PROGRESS' OR t.status='DONE'))
            """)
    Page<Ticket> findAssigneesTickets(Pageable pageable, Long id);

    @Query("""
            SELECT t FROM Ticket t 
            WHERE t.status='NEW'
            OR (t.approver.id=:id 
            AND (t.status='APPROVED' OR t.status='DECLINED' OR t.status='IN_PROGRESS' OR t.status='DONE'))
            """)
    Page<Ticket> findApproversTickets(Pageable pageable, Long id);
}