package com.example.demo.Exceptions;

public class UserEmailHasNoIdException extends Exception{
    public UserEmailHasNoIdException(String message) {
        super(message);
    }
}
