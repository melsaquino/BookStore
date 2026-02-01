package com.example.demo.exceptions;

public class InvalidRangeException extends RuntimeException {
    public InvalidRangeException(String message) {
        super(message);
    }
}
