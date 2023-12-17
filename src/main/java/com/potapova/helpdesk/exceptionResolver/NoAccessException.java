package com.potapova.helpdesk.exceptionResolver;

public class NoAccessException extends RuntimeException {
    public NoAccessException(String message) {
        super(message);
    }
}