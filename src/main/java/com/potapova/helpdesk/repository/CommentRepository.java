package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTicketId(Long id);
}
