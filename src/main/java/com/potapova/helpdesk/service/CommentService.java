package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment, Long userId, Long ticketId);

    List<Comment> getCommentsListByTicketId(Long ticketId, Long userId);
}

