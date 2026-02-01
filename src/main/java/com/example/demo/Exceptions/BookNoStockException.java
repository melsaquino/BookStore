package com.example.demo.Exceptions;

public class BookNoStockException extends Exception {
    public BookNoStockException(String message) {
        super(message);
    }

    public BookNoStockException(String title,int quantity){
      super("Not Enough Stock to Reduce "+title+" Quantity by "+quantity);
    }
}
