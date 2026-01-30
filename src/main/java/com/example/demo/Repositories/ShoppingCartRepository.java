package com.example.demo.Repositories;

import com.example.demo.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findShoppingCartByUserId(int user_id);

}
