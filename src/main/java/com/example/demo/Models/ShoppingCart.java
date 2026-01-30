package com.example.demo.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.example.demo.Models.User;

@Entity
@Table(name="shopping_cart_books")
public class ShoppingCart {
    @Id
    @Getter
    @Column(nullable=false)
    private int id;

    @Getter
    @Setter
    @Column(nullable=false)
    private int userId;

    @Getter
    @Setter
    @Column(nullable=false)
    private String bookIsbn;




}
