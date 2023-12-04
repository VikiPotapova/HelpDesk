package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(Feedback feedback, Long userId, Long ticketId);
    Feedback getFeedbackByTicketId(Long ticketId, Long userId);
    List<Feedback> getAssigneeFeedbackList(Long assigneeId, Long userId);
}
