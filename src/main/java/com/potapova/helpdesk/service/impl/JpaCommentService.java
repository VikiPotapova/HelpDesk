package com.potapova.helpdesk.service.impl;

import com.potapova.helpdesk.domain.Comment;
import com.potapova.helpdesk.domain.Role;
import com.potapova.helpdesk.domain.User;
import com.potapova.helpdesk.exceptionResolver.CommentNotFoundException;
import com.potapova.helpdesk.exceptionResolver.NoAccessException;
import com.potapova.helpdesk.repository.CommentRepository;
import com.potapova.helpdesk.service.AccessService;
import com.potapova.helpdesk.service.CommentService;
import com.potapova.helpdesk.service.TicketService;
import com.potapova.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        User user = userService.getCurrentUser();
        if (!accessService.isUserBelongToTicket(user, ticketId)) {
            throw new NoAccessException("The user with login: " + user.getEmail() + " has no access to leave a comment");
        }
        comment.setUser(user);
        comment.setTicket(ticketService.getTicketById(ticketId));
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Page<Comment> getCommentsListByTicketId(Pageable pageable, Long ticketId) {
        User user = userService.getCurrentUser();
        if (!accessService.isUserBelongToTicket(user, ticketId)) {
            throw new NoAccessException("The user with login: " + user.getEmail() + " has no access to get this comment");
        }
        return commentRepository.findByTicketId(pageable, ticketId);
    }

    @Override
    public void deleteCommentById(Long id) {
        User user = userService.getCurrentUser();
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        if (!(comment.getUser().equals(user) || user.getRole().equals(Role.MANAGER))) {
            throw new NoAccessException("The user with login: " + user.getEmail() + " has no access to delete this comment");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public void updateCommentText(Long id, String text) {
        User user = userService.getCurrentUser();
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        if (!(comment.getUser().equals(user))) {
            throw new NoAccessException("The user with login: " + user.getEmail() + " has no access to update this comment");
        }
        comment.setText(text);
        commentRepository.save(comment);
    }
}