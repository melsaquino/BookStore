package com.example.demo.Controllers;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Exceptions.UserEmailHasNoIdException;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.BooksService;
import com.example.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookStoreController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @Autowired
    private UserRepository userRepository;

    public BookStoreController(){}
    @GetMapping("/")
   /**
    * Redirects url '/' to the method that renders index.html*/
    public String showHome(){
        return "redirect:/books";

    }
    /**
     * Calls the front end of that shows all books in index.html
     *
     * */

    @GetMapping("/books")
    public String showCatalogue(Model model, HttpSession session) throws UserEmailHasNoIdException {

        UserService userService;
        userService = new UserService(userRepository);

        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        if (currentUserId ==-1){
            throw new UserEmailHasNoIdException("Your email does not have an Id associated with it");
        }
        model.addAttribute("userId",currentUserId);

        return "index";
    }


}

