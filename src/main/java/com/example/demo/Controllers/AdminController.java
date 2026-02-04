package com.example.demo.Controllers;

import com.example.demo.Exceptions.*;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.BooksService;
import com.example.demo.Services.RegistrationService;
import com.example.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    BooksCatalogueRepository booksCatalogueRepository;
    @GetMapping("/admin")
    public String showAdminAddUser(HttpSession session, Model model) throws UserEmailHasNoIdException {
        UserService userService;
        userService = new UserService(userRepository);
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        model.addAttribute("userId",currentUserId);
        String currentUserRole = userService.findUserRole((String)session.getAttribute("userEmail"));
        if (currentUserId ==-1){
            throw new UserEmailHasNoIdException("Your email does not have an Id associated with it");
        }
        if(!currentUserRole.equals("ADMIN"))
            return "redirect:/books";
        model.addAttribute("admin",true);
            return "admin";

    }
    @PostMapping("/admin/registration")
    public String createUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("psw_repeat") String psw_repeat,
                             @RequestParam("role") String role,Model model,HttpSession session){
        System.out.println(role);
        System.out.println("Got here");
        RegistrationService registrationService;
        UserService userService;
        userService = new UserService(userRepository);
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));

        registrationService = new RegistrationService(userRepository);
        try{
            registrationService.registerUser(currentUserId,email.toLowerCase(),password,psw_repeat,role);
            model.addAttribute("successMessage","Account successfully created");
            return "admin";

        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            if (e instanceof InvalidAccessException){
                return "index";

            }
            return "admin";
        }

    }
    @PostMapping("/admin/add_book")
    public String createBook(@RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("category") String category,
                             @RequestParam("price") String price, @RequestParam("description") String description,@RequestParam("stock") String stock, Model model,HttpSession session){

        BooksService booksService;
        booksService = new BooksService(booksCatalogueRepository,userRepository);
        UserService userService;
        userService = new UserService(userRepository);
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));

        try{
            booksService.addBooks(currentUserId,title,author, category,price, description,stock);
            model.addAttribute("successMessage","Book successfully created");
            return "admin";

        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            if (e instanceof InvalidAccessException){
                return "index";
            }
            return "admin";
        }

    }
}
