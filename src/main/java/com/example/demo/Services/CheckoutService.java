package com.example.demo.Services;

import com.example.demo.Entities.Book;
import com.example.demo.Entities.Order;
import com.example.demo.Entities.ShoppingCart;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Exceptions.BookDoesNotExist;
import com.example.demo.Exceptions.BookNoStockException;
import com.example.demo.Exceptions.UserDoesNotExist;

import java.util.List;
/**
 * Service class that serves the checkout functionalities
 * */
public class CheckoutService {
    private final BooksCatalogueRepository booksCatalogueRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrdersRepository ordersRepository;
    public CheckoutService(ShoppingCartRepository shoppingCartRepository,BooksCatalogueRepository booksCatalogueRepository,
                           UserRepository userRepository,OrdersRepository ordersRepository){
        this.shoppingCartRepository=shoppingCartRepository;
        this.booksCatalogueRepository=booksCatalogueRepository;
        this.userRepository=userRepository;
        this.ordersRepository=ordersRepository;
    }

    /**
     * Service Function that processes the user wanting to check out all items in their shopping cart
     * @param userId the user's id that will have their shopping cart cleared
     * */
    public void processCheckout(int userId) throws BookDoesNotExist, BookNoStockException {
        if (isUserExists(userId)){
            //check if user has items in the shopping cart
            if(shoppingCartRepository.findShoppingCartByUserId(userId)!=null){
                List<ShoppingCart> userShoppingCart =shoppingCartRepository.findShoppingCartByUserId(userId);
                for(ShoppingCart shoppingCartEntry:userShoppingCart){
                    //check if book is still in stock
                    if (isBookExists(shoppingCartEntry.getBookIsbn())){
                        Book shoppingCartBook =booksCatalogueRepository.findByIsbn(shoppingCartEntry.getBookIsbn());
                        //check if the current stock of books has less books than what is in the shopping cart
                        if (shoppingCartBook.getStock()<shoppingCartEntry.getQuantity()){
                            shoppingCartRepository.delete(shoppingCartEntry);
                            throw new BookNoStockException(shoppingCartBook.getTitle(),shoppingCartEntry.getQuantity());
                        }
                        else
                            shoppingCartBook.reduceStock(shoppingCartEntry.getQuantity());

                        Order order= new Order();
                        order.setCustomerId(userId);
                        order.setBookIsbn(shoppingCartEntry.getBookIsbn());
                        order.setQuantity(shoppingCartEntry.getQuantity());
                        order.setTotal(shoppingCartEntry.getQuantity()*shoppingCartBook.getPrice());

                        ordersRepository.save(order);
                        booksCatalogueRepository.save(shoppingCartBook);
                        shoppingCartRepository.delete(shoppingCartEntry);
                    }
                    else{
                        throw new BookDoesNotExist(shoppingCartEntry.getBookIsbn());
                    }
                }
            }
        }
        else throw new UserDoesNotExist("User does not exist to make that transaction");

    }
    /**
     * Method used to check if the book actually exists in the database
     * @param isbn the book's unique isb is the identifier for the repository to quer
     * */
    private boolean isBookExists(int isbn){
        return (booksCatalogueRepository.findByIsbn(isbn)!=null);
    }
    /**
     * Method used to check if the user actually exists in the database
     * @param user_id the user's unique ID is the identifier for the repository to query
     * */
    private boolean isUserExists(int user_id){
        return (userRepository.findById(user_id)!=null);

    }
}