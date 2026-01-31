package com.example.demo.Services;

import com.example.demo.Entities.Book;
import com.example.demo.Entities.ShoppingCart;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final BooksCatalogueRepository booksCatalogueRepository;
    private final UserRepository userRepository;
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,BooksCatalogueRepository booksCatalogueRepository,
                               UserRepository userRepository){
        this.shoppingCartRepository=shoppingCartRepository;
        this.booksCatalogueRepository=booksCatalogueRepository;
        this.userRepository=userRepository;
    }
    public Map<Book, Integer> getShoppingCartBooks(int user_id ){
        List<ShoppingCart> userShoppingCart=shoppingCartRepository.findShoppingCartByUserId(user_id);
        Map<Book,Integer> booksInCart = new HashMap<>();
        for (ShoppingCart cartItem:userShoppingCart){
            Book book=booksCatalogueRepository.findByIsbn(cartItem.getBookIsbn());
            booksInCart.put(book,cartItem.getQuantity());
        }
        return booksInCart;

    }
    public void AddToShoppingCart(int user_id,int isbn) {
        ShoppingCart shoppingCartEntry;
        //check if user and book isbn are real
        if(userRepository.findById(user_id)!=null && booksCatalogueRepository.findByIsbn(isbn)!=null){
            //check if the user already has this specific book in their cart
            if (shoppingCartRepository.findShoppingCartByUserIdAndBookIsbn(user_id,isbn)!=null){


                shoppingCartEntry =shoppingCartRepository.findShoppingCartByUserIdAndBookIsbn(user_id,isbn);

                shoppingCartEntry.addQuantity();
                this.shoppingCartRepository.save(shoppingCartEntry);
            }else{
                shoppingCartEntry = new ShoppingCart();
                shoppingCartEntry.setBookIsbn(isbn);
                shoppingCartEntry.setUserId(user_id);
                this.shoppingCartRepository.save(shoppingCartEntry);
            }

        }
        else
            System.out.println("invalid entries");

    }
    /*public String deleteFromCart(String book_isbn,int id){
        if (shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn)!=null){
            if(shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn)!
        }
    }*/
    public ShoppingCart getShoppingCartByIsbn(int book_isbn){
        if(shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn)!=null)
            return shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn);
        else {
            System.out.println("invalid");
            return null;
        }

    }

}
