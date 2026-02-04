package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Class that represents the books table
 * */
@Entity
@Table(name="books")
public class Book {
    @Id
    @Getter
    @Setter
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int isbn;

    @Getter
    @Setter
    @Column(nullable = false)
    private String author;

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(nullable = false)
    private double price;

    @Getter
    @Setter
    @Column(nullable = false)
    private String description;

    @Getter
    @Setter
    @Column(nullable = false)
    private String category;

    @Getter
    @Setter
    private int stock;
    public Book(){

    }
    public Book(String author, String title){
        this.author= author;
        this.title=title;
        stock=1;
    }
    public Book(int isbn,String author, String title){
        this.isbn =isbn;
        this.author= author;
        this.title=title;
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
