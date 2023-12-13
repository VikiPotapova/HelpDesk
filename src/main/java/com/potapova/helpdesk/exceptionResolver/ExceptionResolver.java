package com.potapova.helpdesk.exceptionResolver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {
    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<String> noAccessByIdException(NoAccessException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IncorrectStatusException.class)
    public ResponseEntity<String> incorrectStatusException(IncorrectStatusException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameUserInDatabaseException.class)
    public ResponseEntity<String> sameUserInDatabaseException(SameUserInDatabaseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}