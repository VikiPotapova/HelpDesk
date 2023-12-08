package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.*;
import com.potapova.helpdesk.exceptionResolver.FeedbackNotFoundException;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.repository.FeedbackRepository;
import com.potapova.helpdesk.service.AccessService;
import com.potapova.helpdesk.service.FeedbackService;
import com.potapova.helpdesk.service.TicketService;
import com.potapova.helpdesk.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JpaFeedbackService implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final TicketService ticketService;
    private final AccessService accessService;

    @Transactional
    @Override
    public Feedback createFeedback(Feedback feedback, Long userId, Long ticketId) {
        User user = userService.getUserById(userId);
        Ticket ticket = ticketService.getTicketById(ticketId, userId);
        if (!(user.equals(ticket.getOwner()) && ticket.getStatus().equals(Status.DONE))) {
            throw new NoAccessByIdException("User with id: " + userId + " has no access to create a feedback");
        }
        feedback.setUser(user);
        feedback.setTicket(ticket);
        feedbackRepository.save(feedback);
        return feedback;
    }

    @Override
    public Feedback getFeedbackByTicketId(Long ticketId, Long userId) {
        if (!(accessService.checkIfUserBelongToTicket(userId, ticketId))) {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to get this feedback");
        } else if (Objects.isNull(feedbackRepository.findByTicketId(ticketId))) {
            throw new FeedbackNotFoundException("Feedback to ticket with id: " + ticketId + " not found");
        }
        return feedbackRepository.findByTicketId(ticketId);
    }

    @Override
    public List<Feedback> getAssigneeFeedbackList(Long assigneeId, Long userId) {
        User user = userService.getUserById(userId);
        if (!(user.getRole().equals(Role.MANAGER) || assigneeId.equals(userId))) {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to get these feedbacks");
        } else if (Objects.isNull(feedbackRepository.findAllByUserId(assigneeId))) {
            throw new FeedbackNotFoundException("Feedbacks are not found");
        }
        return feedbackRepository.findAllByUserId(assigneeId);
    }
}