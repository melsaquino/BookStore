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

import java.util.List;

@Controller
public class BookStoreController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @Autowired
    private UserRepository userRepository;

    public BookStoreController(){}
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



}

