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
            WHERE t.owner.email=:login
            """)
    Page<Ticket> findOwnersTickets(Pageable pageable, String login);

    @Query("""
            SELECT t FROM Ticket t
            LEFT JOIN t.assignee 
            WHERE t.status='APPROVED'
            OR (t.assignee.email=:login 
            AND (t.status='IN_PROGRESS' OR t.status='DONE'))
            """)
    Page<Ticket> findAssigneesTickets(Pageable pageable, String login);

    @Query("""
            SELECT t FROM Ticket t
            LEFT JOIN t.approver
            WHERE t.status='NEW' 
            OR (t.approver.email=:login 
            AND (t.status='APPROVED' OR t.status='DECLINED' OR t.status='IN_PROGRESS' OR t.status='DONE'))
            """)
    Page<Ticket> findApproversTickets(Pageable pageable, String login);
}