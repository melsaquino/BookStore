package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShoppingCartController {
    @GetMapping("/api/shopping_cart/{user}")
    public String showShoppingCart(@PathVariable("user") int user_id){
        return "shoppingCart";
    }
}
