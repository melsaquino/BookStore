package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Class that represents the shopping_cart_books_table that contains all entries in all user's shopping cart.
 * */
@Entity
@Table(name="shopping_cart_books")
public class ShoppingCart {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private int shoppingId;

    @Getter
    @Setter
    @Column(nullable=false)
    private int userId;

    @Getter
    @Setter
    @Column(nullable=false)
    private int bookIsbn;

    @Getter
    @Setter
    @Column(nullable =false)
    private int quantity=1;

    public void addQuantity(){
        this.quantity++;
    }
    public void decreaseQuantity(){
        this.quantity--;
    }


}
