package com.example.demo.Controllers;
import com.example.demo.DTO.ShoppingCartEntryDTO;

import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.ShoppingCartService;
import com.example.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShoppingCartController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingRepository;

    /**
     * Triggers the shoppingCart.html to be shown to user
     * @param userId used to make sure the correct user's shopping cart is shown as well as sent to html that is needed in the dynamic links.
     * */

    @GetMapping("/shopping_cart/{userId}")
    public String showShoppingCart(@PathVariable("userId") int userId, Model model, HttpSession session){
        UserService userService = new UserService(userRepository);
        if(userService.findUser((String)session.getAttribute("userEmail"))==userId){
            model.addAttribute("userId",userId);
            return "shoppingCart";
        }
        return "redirect:/logout";

    }
    /**
     * Controller that will add books to a users shopping cart
     * */
    @PostMapping("/add_cart/{userId}/{bookIsbn}")
    public String addToCart(@PathVariable("userId") int userId, @PathVariable("bookIsbn") int bookIsbn, Model model ){
        ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingRepository,booksCatalogueRepository,userRepository);
        try{
           shoppingCartService.AddToShoppingCart(userId,bookIsbn);
            return "redirect:/shopping_cart/{userId}";
        }catch(Exception e){
           model.addAttribute("errorMessage",e.getMessage());
           return "index";
        }
    }

    @DeleteMapping("/delete_cart_book/{bookIsbn}")
    public String deleteFromShoppingCart(){
        return "redirect:/shopping_cart/{userId}";
    }
}
