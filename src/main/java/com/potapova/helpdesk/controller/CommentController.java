package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Comment;
import com.potapova.helpdesk.domain.dto.CommentOfTicketDTO;
import com.potapova.helpdesk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    @PostMapping("/{userId}/tickets/{ticketId}")
    public ResponseEntity<CommentOfTicketDTO> createComment(
            @PathVariable Long userId, @PathVariable Long ticketId, @RequestBody CommentOfTicketDTO commentOfTicketDTO) {
        Comment comment = commentService.createComment(modelMapper.map(commentOfTicketDTO, Comment.class), userId, ticketId);
        return new ResponseEntity<>(modelMapper.map(comment, CommentOfTicketDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<List<CommentOfTicketDTO>> getCommentsListByTicketId(
            @PathVariable Long ticketId, @RequestParam Long userId) {
        return new ResponseEntity<>(commentService.getCommentsListByTicketId(ticketId, userId).stream()
                .map(comment -> modelMapper.map(comment, CommentOfTicketDTO.class)).toList(), HttpStatus.OK);
    }

}
