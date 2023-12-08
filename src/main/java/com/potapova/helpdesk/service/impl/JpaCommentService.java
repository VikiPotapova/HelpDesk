package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.Comment;
import com.potapova.helpdesk.exceptionResolver.NoAccessByIdException;
import com.potapova.helpdesk.repository.CommentRepository;
import com.potapova.helpdesk.service.AccessService;
import com.potapova.helpdesk.service.CommentService;
import com.potapova.helpdesk.service.TicketService;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaCommentService implements CommentService {

    private final TicketService ticketService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final AccessService accessService;

    @Override
    public Comment createComment(Comment comment, Long userId, Long ticketId) {
        if (!accessService.checkIfUserBelongToTicket(userId, ticketId)) {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to leave a comment");
        }
        comment.setUser(userService.getUserById(userId));
        comment.setTicket(ticketService.getTicketById(ticketId, userId));
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Page<Comment> getCommentsListByTicketId(Pageable pageable, Long ticketId, Long userId) {
        if (!accessService.checkIfUserBelongToTicket(userId, ticketId)) {
            throw new NoAccessByIdException("The user with id: " + userId + " has no access to get this feedback");
        }
        return commentRepository.findByTicketId(pageable, ticketId);
    }
}