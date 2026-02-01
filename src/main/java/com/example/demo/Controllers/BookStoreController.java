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
    public String showHome(){
        return "redirect:/books";

    }
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
    /*
   @GetMapping("/books/filtered")
    public String showFilteredCatalogue(@RequestParam("author")String author,@RequestParam("priceRange")String priceRange,
                                        @RequestParam String category,Model model,HttpSession session){
        BooksService booksService = new BooksService(booksCatalogueRepository);
        UserService userService = new UserService(userRepository);
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        try{
            List<BookDTO> books = booksService.getFilteredBooks(author,category,priceRange);
            model.addAttribute("books",books);
        }catch(Exception e){
            model.addAttribute("errorMessage",e.getMessage());

        }
        model.addAttribute("userId",currentUserId);
        //return "index";
    }*/
    /*@GetMapping("/books/search")
    public String showSearchResults(@RequestParam("query")String query, Model model,HttpSession session){
        UserService userService = new UserService(userRepository);
        BooksService booksService = new BooksService(booksCatalogueRepository);

        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        List<BookDTO> books = booksService.getSearchResultsBooks(query);
        model.addAttribute("books",books);

        model.addAttribute("userId",currentUserId);
        return "index";

    }*/

}

