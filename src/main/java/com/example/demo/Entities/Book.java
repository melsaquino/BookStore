package com.example.demo.Entities;

public class Book {
    private String isbn;
    private String author;
    private String title;
    private double price;
    public Book(){

    }
    public Book(String author, String title,double price){
        this.author= author;
        this.title=title;
        this.price = price;
    }

}
