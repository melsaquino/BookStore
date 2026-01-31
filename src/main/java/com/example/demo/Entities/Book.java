package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="books")
public class Book {
    @Id
    @Getter
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int isbn;

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

    @Getter
    @Setter
    private int stock;
    public Book(){

    }
    public Book(String author, String title,double price){
        this.author= author;
        this.title=title;
        this.price = price;
        stock=1;
    }
    public Book(int isbn,String author, String title,double price){
        this.isbn =isbn;
        this.author= author;
        this.title=title;
        this.price = price;
        stock=1;
    }

    public void addStock(){
        this.stock++;
    }
    public void addStock(int stock){
        this.stock+=stock;
    }
    public void reduceStock(){
        this.stock--;
    }
    public void reduceStock(int stock){
        this.stock-=stock;
    }

}
