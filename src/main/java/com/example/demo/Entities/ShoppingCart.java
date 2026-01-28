package com.example.demo.Entities;

import jakarta.persistence.Id;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
//@GeneratedValue
//@Table(name="ShoppingCart")
public class ShoppingCart {
    //@Id
    private int id;
    private int user;

    public ShoppingCart(int user_id){
        this.user= user_id;
    }

}
