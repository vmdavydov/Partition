package com.apsoft.partition.exceptions;

public class NotSupportedFormatException extends RuntimeException {
    public NotSupportedFormatException() {
    }

    public NotSupportedFormatException(String message) {
        super(message);
    }
}
