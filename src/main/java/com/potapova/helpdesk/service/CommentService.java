package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment createComment(Comment comment, Long ticketId);

    Page<Comment> getCommentsListByTicketId(Pageable pageable, Long ticketId);

    void deleteCommentById(Long id);

    void updateCommentText(Long id, String text);
}