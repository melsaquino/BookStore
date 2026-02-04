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
   /**
    * Redirects url '/' to the method that renders index.html*/
   @GetMapping("/")
   public String showHome(){
        return "redirect:/books";

    }
    /**
     * Calls the front end of that shows all books in index.html
     *
     * */
    @GetMapping("/books")
    public String showCatalogue(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "4") int size,Model model, HttpSession session) throws UserEmailHasNoIdException {

        UserService userService;
        userService = new UserService(userRepository);
        String currentUserRole = userService.findUserRole((String)session.getAttribute("userEmail"));

        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        if (currentUserId ==-1){
            throw new UserEmailHasNoIdException("Your email does not have an Id associated with it");
        }
        if(currentUserRole.equals("ADMIN"))
            model.addAttribute("admin",true);

        model.addAttribute("userId",currentUserId);
        model.addAttribute("page",page);
        model.addAttribute("size",size);

        return "index";
    }


}

