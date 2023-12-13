package com.potapova.helpdesk.exceptionResolver;

public class TicketNotFoundException extends NotFoundException {
    public TicketNotFoundException(String message) {
        super(message);
    }
}