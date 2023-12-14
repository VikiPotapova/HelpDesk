package com.potapova.helpdesk.exceptionResolver;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
