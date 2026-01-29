package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String email;

    @Setter
    @Getter
    @Column(nullable = false)
    private String password;

    private String shoppingCartId;

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCartId = shoppingCart.getId();
    }
    public void setShoppingCart(){

        this.shoppingCartId = new ShoppingCart(this.id).getId();
    }
}
