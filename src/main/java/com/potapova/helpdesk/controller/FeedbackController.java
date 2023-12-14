package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Feedback;
import com.potapova.helpdesk.domain.dto.FeedbackDTO;
import com.potapova.helpdesk.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ModelMapper modelMapper;

    @PostMapping("/tickets/{ticketId}")
    public ResponseEntity<FeedbackDTO> createFeedback(
            @PathVariable Long ticketId, @RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.createFeedback(modelMapper.map(feedbackDTO, Feedback.class), ticketId);
        return new ResponseEntity<>(modelMapper.map(feedback, FeedbackDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackByTicketId(@PathVariable Long id) {
        return new ResponseEntity<>(modelMapper.map(feedbackService.getFeedbackByTicketId(id), FeedbackDTO.class), HttpStatus.OK);
    }

    @GetMapping("/users/{assigneeId}")
    public ResponseEntity<Page<FeedbackDTO>> getAssigneeFeedbacks(
            @PageableDefault(value = 5, sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long assigneeId) {
        return new ResponseEntity<>(feedbackService.getAssigneeFeedbacks(pageable, assigneeId)
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)), HttpStatus.OK);
    }
}