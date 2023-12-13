package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.Comment;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.NoAccessException;
import com.potapova.helpdesk.repository.CommentRepository;
import com.potapova.helpdesk.service.AccessService;
import com.potapova.helpdesk.service.CommentService;
import com.potapova.helpdesk.service.TicketService;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaCommentService implements CommentService {

    private final TicketService ticketService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final AccessService accessService;

    @Override
    public Comment createComment(Comment comment, Long ticketId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
        if (!accessService.checkIfUserBelongToTicket(user, ticketId)) {
            throw new NoAccessException("The user with login: " + userLogin + " has no access to leave a comment");
        }
        comment.setUser(user);
        comment.setTicket(ticketService.getTicketById(ticketId));
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Page<Comment> getCommentsListByTicketId(Pageable pageable, Long ticketId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userLogin);
        if (!accessService.checkIfUserBelongToTicket(user, ticketId)) {
            throw new NoAccessException("The user with login: " + userLogin + " has no access to get this feedback");
        }
        return commentRepository.findByTicketId(pageable, ticketId);
    }
}