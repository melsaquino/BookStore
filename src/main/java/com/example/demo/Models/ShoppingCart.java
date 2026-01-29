package com.example.demo.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name="shopping_cart_books")
public class ShoppingCart {
    @Id
    @Getter
    @Column(nullable=false)
    private String id;


    private int user;

    public ShoppingCart(int user_id){
        this.user= user_id;
    }

}
