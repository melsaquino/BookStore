package com.example.demo.Services;

import com.example.demo.DTO.ShoppingCartEntryDTO;
import com.example.demo.Entities.Book;
import com.example.demo.Entities.ShoppingCart;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Exceptions.BookDoesNotExist;
import com.example.demo.Exceptions.UserDoesNotExist;

import java.util.ArrayList;
import java.util.List;
/**
 * Service class that serves shopping cart functionalities
 * */
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
    /**
     * Retrieve all the books and their relevant data from the user's shopping cart
     * @userId the user's customer ID
     * */
    public List<ShoppingCartEntryDTO> getShoppingCartBooks(int userId ){
        List<ShoppingCart> userShoppingCart=shoppingCartRepository.findShoppingCartByUserId(userId);
        List<ShoppingCartEntryDTO> booksInCart= new ArrayList<>();
        for (ShoppingCart cartItem:userShoppingCart){
            Book book=booksCatalogueRepository.findByIsbn(cartItem.getBookIsbn());
            booksInCart.add(new ShoppingCartEntryDTO(cartItem.getShoppingId(),book.getIsbn(), book.getTitle(), book.getAuthor(), book.getCategory(), cartItem.getQuantity(), book.getPrice()));
        }
        return booksInCart;

    }
    /**
     * Handles adding a book into the shopping cart
     * @param user_id the user's ID
     * @param isbn the unique ISBN of the book to be added in the shopping cart
     * */
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

}
