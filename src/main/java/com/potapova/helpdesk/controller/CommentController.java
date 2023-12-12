package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Comment;
import com.potapova.helpdesk.domain.dto.CommentOfTicketDTO;
import com.potapova.helpdesk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping("/users/{userId}/tickets/{ticketId}")
    public ResponseEntity<CommentOfTicketDTO> createComment(
            @PathVariable Long userId, @PathVariable Long ticketId, @RequestBody CommentOfTicketDTO commentOfTicketDTO) {
        Comment comment = commentService.createComment(modelMapper.map(commentOfTicketDTO, Comment.class), userId, ticketId);
        return new ResponseEntity<>(modelMapper.map(comment, CommentOfTicketDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<Page<CommentOfTicketDTO>> getCommentsListByTicketId(
            @PageableDefault(value = 5, sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long ticketId, @RequestParam Long userId) {
        return new ResponseEntity<>(commentService.getCommentsListByTicketId(pageable, ticketId, userId)
                .map(comment -> modelMapper.map(comment, CommentOfTicketDTO.class)), HttpStatus.OK);
    }
}