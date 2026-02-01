package com.example.demo.Controllers.RESTControllers;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.BooksService;
import com.example.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class BooksCatalogueRestController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> showBooks(){
        BooksService booksService = new BooksService(booksCatalogueRepository);
        try{
            List <BookDTO> books = booksService.getAllBooksInStock();
            return ResponseEntity.ok(books);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/books/filtered")
    public ResponseEntity<List<BookDTO>> showFilteredCatalogue(@RequestParam(name= "author",required = false)String author, @RequestParam(name= "priceRange",required = false)String priceRange,
                                        @RequestParam(name = "category",required = false) String category, HttpSession session){
        BooksService booksService = new BooksService(booksCatalogueRepository);
        UserService userService = new UserService(userRepository);
        List<BookDTO> books =new ArrayList<>();
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));

        books = booksService.getFilteredBooks(author,category,priceRange);
        return ResponseEntity.ok(books);
    }
    @GetMapping("/books/search")
    public ResponseEntity<List<BookDTO>>  showSearchResults(@RequestParam("query")String query,HttpSession session){
        UserService userService = new UserService(userRepository);
        BooksService booksService = new BooksService(booksCatalogueRepository);

        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        List<BookDTO> books = booksService.getSearchResultsBooks(query);

        return ResponseEntity.ok(books);
    }
}
