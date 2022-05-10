package com.apsoft.partition.exceptions;

public class WrongNestingSectionException extends RuntimeException {
    public WrongNestingSectionException() {
    }

    public WrongNestingSectionException(String message) {
        super(message);
    }
}
