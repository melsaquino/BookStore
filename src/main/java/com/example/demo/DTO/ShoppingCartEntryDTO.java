package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * Class representing a DTO that holds the data about each user's shopping entry
 * */
public class ShoppingCartEntryDTO implements Serializable {
    @Getter
    @Setter
    private int shoppingId;

    @Getter
    @Setter
    private int bookIsbn;

    @Getter
    @Setter
    private String bookTitle;

    @Getter
    @Setter
    private String bookAuthor;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private int quantity=1;

    @Getter
    @Setter
    private double price;

    public ShoppingCartEntryDTO(int id, int bookIsbn, String title, String author,
                                String category, int quantity, double price){
        this.shoppingId =id;
        this.bookAuthor =author;
        this.category =category;
        this.bookIsbn=bookIsbn;
        this.bookTitle=title;
        this.quantity =quantity;
        this.price =price;
    }
}
