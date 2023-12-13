package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.*;
import com.potapova.helpdesk.exceptionResolver.FeedbackNotFoundException;
import com.potapova.helpdesk.exceptionResolver.NoAccessException;
import com.potapova.helpdesk.repository.FeedbackRepository;
import com.potapova.helpdesk.service.AccessService;
import com.potapova.helpdesk.service.FeedbackService;
import com.potapova.helpdesk.service.TicketService;
import com.potapova.helpdesk.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public Feedback createFeedback(Feedback feedback, Long ticketId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (!(user.equals(ticket.getOwner()) && ticket.getStatus().equals(Status.DONE))) {
            throw new NoAccessException("User with login: " + userLogin + " has no access to create a feedback");
        }
        feedback.setUser(user);
        feedback.setTicket(ticket);
        feedbackRepository.save(feedback);
        return feedback;
    }

    @Override
    public Feedback getFeedbackByTicketId(Long ticketId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
        Feedback feedback = feedbackRepository.findByTicketId(ticketId);
        if (!(accessService.checkIfUserBelongToTicket(user, ticketId))) {
            throw new NoAccessException("The user with login: " + userLogin + " has no access to get this feedback");
        } else if (Objects.isNull(feedback)) {
            throw new FeedbackNotFoundException("Feedback to ticket with id: " + ticketId + " not found");
        }
        return feedback;
    }

    @Override
    public Page<Feedback> getAssigneeFeedbacks(Pageable pageable, Long assigneeId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
        User assignee = userService.getUserById(assigneeId);
        Page<Feedback> feedbacks = feedbackRepository.findAllByUserId(pageable, assigneeId);
        if (!(user.getRole().equals(Role.MANAGER) || user.equals(assignee))) {
            throw new NoAccessException("The user with login: " + userLogin + " has no access to get these feedbacks");
        } else if (Objects.isNull(feedbacks)) {
            throw new FeedbackNotFoundException("Feedbacks are not found");
        }
        return feedbacks;
    }
}