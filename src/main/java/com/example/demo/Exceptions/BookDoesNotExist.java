package com.example.demo.Exceptions;

public class BookDoesNotExist extends Exception {
    public BookDoesNotExist(String message) {
        super(message);
    }
    public BookDoesNotExist(int isbn) {
        super("Book does not exist with ISBN: "+isbn);
    }
}
