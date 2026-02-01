package com.example.demo.Controllers;

import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CheckoutController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/checkout/all")
    public String showCheckout(){

        return "checkout";

    }
    @PostMapping("/checkout/{userId}/all")
    public String checkoutAll(@PathVariable("userId")int userId, Model model){
        CheckoutService checkoutService=new CheckoutService(shoppingRepository,booksCatalogueRepository,
                userRepository,ordersRepository);
        try{
            checkoutService.processCheckout(userId);
            return "redirect:/books";
        }catch(Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "shoppingCart";
        }
    }
}

