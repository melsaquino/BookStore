package com.example.demo.Controllers;
import com.example.demo.Models.Book;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("/api/shopping_cart/{user}")
    public String showShoppingCart(@PathVariable("user") int user_id, Model model, HttpSession session){
        if (session != null && session.getAttribute("loggedIn")!=null && ((boolean) session.getAttribute("loggedIn"))) {
            ShoppingCartService shoppingCartService =new ShoppingCartService(shoppingRepository,booksCatalogueRepository,userRepository);
            List<Book> books = shoppingCartService.getShoppingCartBooks(user_id);
            //System.out.println(books.size());
            model.addAttribute("books",books);
            return "shoppingCart";
        }

        return "redirect:/api/login";
    }

    @PostMapping("/api/add_cart/{user_id}/{book_isbn}")
    public String addToCart(@PathVariable("user_id") int user_id, @PathVariable("book_isbn") String book_isbn ){
        ShoppingCartService shoppingCartService =new ShoppingCartService(shoppingRepository,booksCatalogueRepository,userRepository);
        shoppingCartService.AddToShoppingCart(user_id,book_isbn);
        return "redirect:/api/shopping_cart/{user_id}";
    }
}
