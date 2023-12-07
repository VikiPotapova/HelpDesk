package com.potapova.helpdesk.controller;

import com.potapova.helpdesk.domain.Feedback;
import com.potapova.helpdesk.domain.dto.FeedbackDTO;
import com.potapova.helpdesk.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ModelMapper modelMapper;

    @PostMapping("/{userId}/tickets/{ticketId}")
    public ResponseEntity<FeedbackDTO> createFeedback(
            @PathVariable Long userId, @PathVariable Long ticketId, @RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.createFeedback(modelMapper.map(feedbackDTO, Feedback.class), userId, ticketId);
        return new ResponseEntity<>(modelMapper.map(feedback, FeedbackDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackByTicketId(@PathVariable Long id, @RequestParam Long userId) {
        return new ResponseEntity<>(modelMapper.map(feedbackService.getFeedbackByTicketId(id, userId), FeedbackDTO.class), HttpStatus.OK);
    }

    @GetMapping("/users/{assigneeId}")
    public ResponseEntity<List<FeedbackDTO>> getAssigneeFeedbackList(@PathVariable Long assigneeId, @RequestParam Long userId) {
        return new ResponseEntity<>(feedbackService.getAssigneeFeedbackList(assigneeId, userId).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).toList(), HttpStatus.OK);
    }
}