package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * Class that represent as Book DTO or the data that will be displayed to the users
 * */
public class BookDTO implements Serializable {

    @Getter
    @Setter
    private int isbn;

    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private int stock;

    public BookDTO(int isbn,String title, String author, double price,String description,String category,int stock){
        this.isbn =isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
        this.stock = stock;
    }
}
