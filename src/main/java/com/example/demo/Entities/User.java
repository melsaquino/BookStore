package com.example.demo.Entities;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;


import jakarta.persistence.Entity;
//@Entity
//@Table(name="users")
public class User {
   // @Id
   // @GeneratedValue
    private int id;
    private String email;
    //private String password;
    private ShoppingCart shoppingCart;
    public User(String email){
        this.email = email;

    }
    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }
    public void setShoppingCart(){
        this.shoppingCart = new ShoppingCart(this.id);
    }
}
