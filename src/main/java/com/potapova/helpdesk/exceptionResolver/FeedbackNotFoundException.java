package com.potapova.helpdesk.exceptionResolver;

public class FeedbackNotFoundException extends NotFoundException {
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}