package com.example.demo.Controllers;

import com.example.demo.Entities.Book;
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
    public String showCatalogue(Model model, HttpSession session) {
        BooksService booksService;
        UserService userService;

        booksService = new BooksService(booksCatalogueRepository);
        userService = new UserService(userRepository);
        List<Book> books = booksService.getAllBooksInStock();
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));

        model.addAttribute("userId",currentUserId);
        model.addAttribute("books",books);
        return "index";
    }

    @GetMapping("/books/filtered")
    public String showFilteredCatalogue(@RequestParam("author")String author,@RequestParam("priceRange")String priceRange,
                                        @RequestParam String category,Model model,HttpSession session){
        BooksService booksService = new BooksService(booksCatalogueRepository);
        UserService userService = new UserService(userRepository);
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        try{
            List<Book> books = booksService.getFilteredBooks(author,category,priceRange);
            model.addAttribute("books",books);
        }catch(Exception e){
            model.addAttribute("errorMessage",e.getMessage());

        }
        model.addAttribute("userId",currentUserId);
        return "index";
    }
    @GetMapping("/books/search")
    public String showSearchResults(@RequestParam("query")String query, Model model,HttpSession session){
        UserService userService = new UserService(userRepository);
        BooksService booksService = new BooksService(booksCatalogueRepository);

        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        List<Book> books = booksService.getSearchResultsBooks(query);
        model.addAttribute("books",books);

        model.addAttribute("userId",currentUserId);
        return "index";

    }

}

