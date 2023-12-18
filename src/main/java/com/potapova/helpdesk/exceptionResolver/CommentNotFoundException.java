package com.potapova.helpdesk.exceptionResolver;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(String message) {
        super(message);
    }
}
