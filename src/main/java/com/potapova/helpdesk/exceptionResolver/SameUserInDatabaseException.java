package com.potapova.helpdesk.exceptionResolver;

public class SameUserInDatabaseException extends RuntimeException {
    public SameUserInDatabaseException(String message) {
        super(message);
    }
}
