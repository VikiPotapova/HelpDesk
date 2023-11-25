package com.potapova.helpdesk.exceptionResolver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<HttpStatus> ticketNotFoundException(Exception e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
