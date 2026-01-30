package com.example.demo.Services;

import com.example.demo.Models.Book;
import com.example.demo.Models.ShoppingCart;
import com.example.demo.Models.User;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final BooksCatalogueRepository booksCatalogueRepository;
    private final UserRepository userRepository;
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,BooksCatalogueRepository booksCatalogueRepository,UserRepository userRepository){
        this.shoppingCartRepository=shoppingCartRepository;
        this.booksCatalogueRepository=booksCatalogueRepository;
        this.userRepository=userRepository;
    }
    public List<Book> getShoppingCartBooks(int user_id ){
        List<ShoppingCart> userShoppingCart=shoppingCartRepository.findShoppingCartByUserId(user_id);
        List<Book> booksInCart = new ArrayList<>();
        for (ShoppingCart cart:userShoppingCart){
            Book book=booksCatalogueRepository.findByIsbn(cart.getBookIsbn());
            booksInCart.add(book);
        }
        return booksInCart;


    }
    public void AddToShoppingCart(int user_id,String isbn) {
        if(userRepository.findById(user_id)!=null && booksCatalogueRepository.findByIsbn(isbn)!=null){
            ShoppingCart shoppingCartEntry = new ShoppingCart();
            shoppingCartEntry.setBookIsbn(isbn);
            shoppingCartEntry.setUserId(user_id);
            this.shoppingCartRepository.save(shoppingCartEntry);
        }
        else
            System.out.println("invalid entries");




    }

}
