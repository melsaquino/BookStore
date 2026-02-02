package com.example.demo.Controllers.RESTControllers;

import com.example.demo.DTO.ShoppingCartEntryDTO;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.ShoppingCartService;
import com.example.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/api")
@RestController
public class ShoppingCartRestController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingRepository;
    @GetMapping("/shopping_cart/{userId}")
    public ResponseEntity<List<ShoppingCartEntryDTO>> showShoppingCart(@PathVariable("userId") int userId, HttpSession session){
        UserService userService = new UserService(userRepository);
        if(userService.findUser((String)session.getAttribute("userEmail"))==userId){

            ShoppingCartService shoppingCartService =new ShoppingCartService(shoppingRepository,booksCatalogueRepository,userRepository);
            List<ShoppingCartEntryDTO> books = shoppingCartService.getShoppingCartBooks(userId);

            return ResponseEntity.ok(books);

        }

        return ResponseEntity.status(403).build();

    }
}
