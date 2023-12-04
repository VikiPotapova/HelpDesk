package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Comment;
import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.Ticket;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaCommentService implements CommentService {
    private final TicketService ticketService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment, Long userId, Long ticketId) {
        User userWhoLeaveComment = userService.getUserById(userId);
        Ticket tickerForComment = ticketService.getTicketById(ticketId, userId);
        if (userWhoLeaveComment.equals(tickerForComment.getOwner()) || userWhoLeaveComment.equals(tickerForComment.getApprover())
                || userWhoLeaveComment.equals(tickerForComment.getAssignee())) {

            comment.setUser(userWhoLeaveComment);
            comment.setTicket(tickerForComment);
            commentRepository.save(comment);
        } else {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to leave a comment");
        }
        return comment;
    }

    @Override
    public List<Comment> getCommentsListByTicketId(Long ticketId, Long userId) {
       // User user = userService.getUserById(userId);
        Ticket ticket = ticketService.getTicketById(ticketId, userId);
        /*List<Comment> commentList;
        if (user.equals(ticket.getOwner()) || user.getRole().equals(Role.MANAGER) || user.equals(ticket.getAssignee())) {
            commentList = */
        return commentRepository.findByTicketId(ticketId);
        /*} else {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to get this feedback");
        }
        return commentList;*/
    }


}
