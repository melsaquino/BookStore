package com.example.demo.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name="books")
public class Book {
    @Id
    @Getter
    @Column(nullable = false)
    private String isbn;

    @Getter
    @Column(nullable = false)
    private String author;

    @Getter
    @Column(nullable = false)
    private String title;

    @Getter
    @Column(nullable = false)
    private double price;

    @Getter
    @Column(nullable = false)
    private String description;

    @Getter
    @Column(nullable = false)
    private String category;
    public Book(){

    }
    public Book(String author, String title,double price){
        this.author= author;
        this.title=title;
        this.price = price;
    }
    public Book(String isbn,String author, String title,double price){
        this.isbn =isbn;
        this.author= author;
        this.title=title;
        this.price = price;
    }

}
