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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * Controller that gets data from the POST request to process checkout
 * */
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

    /**
     * makes sure that the correct redirects after adding to cart or going to shopping cart page when there is an issue
     * @param userId used to ensure the correct user is checking out
     *
     * */
    @PostMapping("/checkout/{userId}/all")
    public String checkoutAll(@PathVariable("userId")int userId, RedirectAttributes redirectAttributes){
        CheckoutService checkoutService=new CheckoutService(shoppingRepository,booksCatalogueRepository,
                userRepository,ordersRepository);
        try{
            checkoutService.processCheckout(userId);
            redirectAttributes.addFlashAttribute("successMessage", "Order Successful");
            return "redirect:/books";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/shopping_cart/{userId}";
        }
    }
}

