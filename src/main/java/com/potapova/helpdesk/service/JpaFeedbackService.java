package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Feedback;
import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.FeedbackNotFoundException;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.repository.FeedbackRepository;
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

    @Transactional
    @Override
    public Feedback createFeedback(Feedback feedback, Long userId, Long ticketId) {
        User user = userService.getUserById(userId);
        Ticket ticket = ticketService.getTicketById(ticketId, userId);
        if (user.equals(ticket.getOwner())) {
            feedback.setUser(user);
            feedback.setTicket(ticket);
            feedbackRepository.save(feedback);
        } else {
            throw new NoAccessByIdException("User with id: " + userId + " has no access to create a feedback");
        }
        return feedback;
    }

    @Override
    public Feedback getFeedbackByTicketId(Long ticketId, Long userId) {
        User user = userService.getUserById(userId);
        Ticket ticket = ticketService.getTicketById(ticketId, userId);
        Feedback feedback;
        if (user.equals(ticket.getOwner()) || user.getRole().equals(Role.MANAGER)
                || ticket.getAssignee().equals(user)) {
            feedback = feedbackRepository.findByTicketId(ticketId);
            if (Objects.equals(feedback, null)) {
                throw new FeedbackNotFoundException("Feedback to ticket with id: " + ticketId + " not found");
            }
        } else {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to get this feedback");
        }
        return feedback;
    }

    @Override
    public List<Feedback> getAssigneeFeedbackList(Long assigneeId, Long userId) {
        User user = userService.getUserById(userId);
        List<Feedback> feedbacks;
        if (user.getRole().equals(Role.MANAGER) || assigneeId.equals(userId)) {
            feedbacks = feedbackRepository.findAllByUserId(assigneeId);
            if (Objects.equals(feedbacks, null)) {
                throw new FeedbackNotFoundException("Feedbacks are not found");
            }
            return feedbacks;
        } else {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to get these feedbacks");
        }
    }
}
