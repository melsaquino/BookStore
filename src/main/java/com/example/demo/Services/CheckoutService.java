package com.example.demo.Services;

import com.example.demo.Entities.Book;
import com.example.demo.Entities.Order;
import com.example.demo.Entities.ShoppingCart;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.exceptions.BookDoesNotExist;
import com.example.demo.exceptions.BookNoStockException;

import java.util.List;

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


    //checkout all in the cart
    public void processCheckout(int userId) throws BookDoesNotExist, BookNoStockException {
        if (isUserExists(userId)){
            //check if user has items in the shopping cart
            if(shoppingCartRepository.findShoppingCartByUserId(userId)!=null){
                List<ShoppingCart> userShoppingCart =shoppingCartRepository.findShoppingCartByUserId(userId);
                for(ShoppingCart shoppingCartEntry:userShoppingCart){
                    //check if book is still in stock
                    if (isBookExists(shoppingCartEntry.getBookIsbn())){
                        Book shoppingCartBook =booksCatalogueRepository.findByIsbn(shoppingCartEntry.getBookIsbn());
                        if (shoppingCartBook.getStock()<shoppingCartEntry.getQuantity()){
                            shoppingCartRepository.delete(shoppingCartEntry);
                            throw new BookNoStockException(shoppingCartEntry.getQuantity());
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

    }

    private boolean isBookExists(int isbn){
        return (booksCatalogueRepository.findByIsbn(isbn)!=null);
    }
    private boolean isUserExists(int user_id){
        return (userRepository.findById(user_id)!=null);

    }
}