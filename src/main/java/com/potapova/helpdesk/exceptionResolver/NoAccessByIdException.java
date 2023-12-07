package com.potapova.helpdesk.exceptionResolver;

public class NoAccessByIdException extends RuntimeException{
    public NoAccessByIdException(String message) {
        super(message);
    }
}