package com.potapova.helpdesk.exceptionResolver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<String> ticketNotFoundException(TicketNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAccessByIdException.class)
    public ResponseEntity<String> noAccessByIdException(NoAccessByIdException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IncorrectStatusException.class)
    public ResponseEntity<String> incorrectStatusException(IncorrectStatusException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeedbackNotFoundException.class)
    public ResponseEntity<String> feedbackNotFoundException(FeedbackNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}