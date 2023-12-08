package com.potapova.helpdesk.exceptionResolver;

public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}