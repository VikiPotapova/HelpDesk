package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Comment;
import com.potapova.helpdesk.domain.dto.CommentOfTicketDTO;
import com.potapova.helpdesk.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping("/tickets/{ticketId}")
    public ResponseEntity<CommentOfTicketDTO> createComment(
            @PathVariable Long ticketId, @RequestBody @Valid CommentOfTicketDTO commentOfTicketDTO) {
        Comment comment = commentService.createComment(modelMapper.map(commentOfTicketDTO, Comment.class), ticketId);
        return new ResponseEntity<>(modelMapper.map(comment, CommentOfTicketDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<Page<CommentOfTicketDTO>> getCommentsListByTicketId(
            @PageableDefault(value = 5, sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long ticketId) {
        return new ResponseEntity<>(commentService.getCommentsListByTicketId(pageable, ticketId)
                .map(comment -> modelMapper.map(comment, CommentOfTicketDTO.class)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCommentText(@PathVariable Long id, @RequestParam String newText) {
        commentService.updateCommentText(id, newText);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}