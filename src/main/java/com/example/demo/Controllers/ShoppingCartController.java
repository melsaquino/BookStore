package com.example.demo.Controllers;
import com.example.demo.Entities.Book;

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
import java.util.Map;

@Controller
public class ShoppingCartController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingRepository;

    @GetMapping("/shopping_cart/{user}")
    public String showShoppingCart(@PathVariable("user") int userId, Model model, HttpSession session){
        UserService userService = new UserService(userRepository);
        if(userService.findUser((String)session.getAttribute("userEmail"))==userId){
            try{
                ShoppingCartService shoppingCartService =new ShoppingCartService(shoppingRepository,booksCatalogueRepository,userRepository);
                Map<Book,Integer> books = shoppingCartService.getShoppingCartBooks(userId);
                model.addAttribute("userId",userId);
                model.addAttribute("books",books);
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
            }
            return "shoppingCart";
        }
        return "redirect:/logout";

    }

    @PostMapping("/add_cart/{userId}/{bookIsbn}")
    public String addToCart(@PathVariable("userId") int userId, @PathVariable("bookIsbn") int bookIsbn, Model model ){
        ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingRepository,booksCatalogueRepository,userRepository);
        try{
           shoppingCartService.AddToShoppingCart(userId,bookIsbn);
        }catch(Exception e){
           model.addAttribute("errorMessage",e.getMessage());
           return "index";
        }
        return "redirect:/shopping_cart/{userId}";
    }

    @DeleteMapping("/delete_cart_book/{bookIsbn}")
    public String deleteFromShoppingCart(){
        return "redirect:/shopping_cart/{userId}";
    }
}
