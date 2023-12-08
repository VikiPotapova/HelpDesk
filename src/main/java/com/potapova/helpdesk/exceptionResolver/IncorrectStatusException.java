package com.potapova.helpdesk.exceptionResolver;

public class IncorrectStatusException extends RuntimeException {
    public IncorrectStatusException(String message) {
        super(message);
    }
}