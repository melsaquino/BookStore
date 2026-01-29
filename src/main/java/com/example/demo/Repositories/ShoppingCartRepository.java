package com.example.demo.Repositories;

import com.example.demo.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    ShoppingCart findShoppingCartByUserId();
}
