package com.potapova.helpdesk.exceptionResolver;

public class NoAccessByIdException extends RuntimeException{
    private Long id;

    public NoAccessByIdException(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NoAccessByIdException{" +
                "id=" + id +
                '}';
    }
}
