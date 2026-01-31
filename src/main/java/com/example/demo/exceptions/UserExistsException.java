package com.example.demo.exceptions;

public class UserExistsException extends Exception{
    public UserExistsException(String message) {
        super(message);
    }
}
