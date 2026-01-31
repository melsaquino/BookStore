package com.example.demo.Repositories;

import com.example.demo.Entities.Book;
import com.example.demo.Entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findShoppingCartByUserId(int user_id);

    Void deleteShoppingCartByBookIsbn(int bookIsbn);
    ShoppingCart findShoppingCartByBookIsbn(int bookIsbn);
    ShoppingCart findShoppingCartByUserIdAndBookIsbn(int id,int isbn);

    ShoppingCart getShoppingCartByBookIsbn(int bookIsbn);


}
