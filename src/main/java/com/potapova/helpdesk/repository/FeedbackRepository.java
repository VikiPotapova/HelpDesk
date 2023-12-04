package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    Feedback findByTicketId(Long id);
    @Query(value = "select f from Feedback f where f.ticket.assignee.id=:id")
    List<Feedback> findAllByUserId(Long id);
}
