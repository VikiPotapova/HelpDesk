package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {
    Feedback createFeedback(Feedback feedback, Long ticketId);

    Feedback getFeedbackByTicketId(Long ticketId);

    Page<Feedback> getAssigneeFeedbacks(Pageable pageable, Long assigneeId);
}