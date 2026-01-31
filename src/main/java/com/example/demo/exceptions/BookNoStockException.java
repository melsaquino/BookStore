package com.example.demo.exceptions;

public class BookNoStockException extends Exception {
    public BookNoStockException(String message) {
        super(message);
    }

    public BookNoStockException(int quantity){
      super("Not Enough Stock to Reduce Quantity by "+quantity);
    }
}
