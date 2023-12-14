package com.potapova.helpdesk.exceptionResolver;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
