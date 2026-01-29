package com.example.demo.Controllers;

import com.example.demo.Models.Book;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Services.BooksService;
import com.example.demo.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookStoreController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    public BookStoreController(){}
    @GetMapping("/api/catalogue")
    public String showCatalogue(Model model) {
        BooksService booksService;
        booksService = new BooksService(booksCatalogueRepository);
        List<Book> books = booksService.getAllBooks();
        model.addAttribute("books",books);
        return "index";
    }

    //public ArrayList<Book> getAllBooks(){

    //}

}

