package com.example.demo.Services;

import com.example.demo.Entities.Book;
import com.example.demo.Entities.ShoppingCart;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.exceptions.BookDoesNotExist;
import com.example.demo.exceptions.UserDoesNotExist;

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
    public void AddToShoppingCart(int user_id,int isbn) throws BookDoesNotExist {
        ShoppingCart shoppingCartEntry;
        //check if user and book isbn are real
        if(userRepository.findById(user_id)!=null && booksCatalogueRepository.findByIsbn(isbn)!=null){
            //check if the user already has this specific book in their cart
            if (shoppingCartRepository.findShoppingCartByUserIdAndBookIsbn(user_id,isbn)!=null){
                //check if quantity will exceed book stock if it is added to cart
                shoppingCartEntry =shoppingCartRepository.findShoppingCartByUserIdAndBookIsbn(user_id,isbn);
                if(shoppingCartEntry.getQuantity()+1 <= booksCatalogueRepository.findByIsbn(isbn).getStock()){
                    shoppingCartEntry.addQuantity();
                    this.shoppingCartRepository.save(shoppingCartEntry);
                }

            }else{
                shoppingCartEntry = new ShoppingCart();
                shoppingCartEntry.setBookIsbn(isbn);
                shoppingCartEntry.setUserId(user_id);
                this.shoppingCartRepository.save(shoppingCartEntry);
            }

        }
        else{
            if(booksCatalogueRepository.findByIsbn(isbn)==null)
                throw new BookDoesNotExist(isbn);
            else throw new UserDoesNotExist("The user does not exist to make that transaction");
        }

    }
    /*public String deleteFromCart(String book_isbn,int id){
        if (shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn)!=null){
            if(shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn)!
        }
    }*/
    public ShoppingCart getShoppingCartByIsbn(int book_isbn) throws BookDoesNotExist {
        if(shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn)!=null)
            return shoppingCartRepository.findShoppingCartByBookIsbn(book_isbn);
        else {
            throw new BookDoesNotExist("Book does not exist in shoppingCart");
        }

    }

}
