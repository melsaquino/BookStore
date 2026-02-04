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
/**
 * This class represents the REST controllers used to retrieve data primarily data from the books databas.
 *
 */
@RestController
@RequestMapping("/api/")
public class BooksCatalogueRestController {
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;

    @Autowired
    private UserRepository userRepository;
    /**
     * This method is used to retrieve all the books that are in stock
     * @return A response entity that communicates the http status and the booksDTO will be in the body
     *  */
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> showBooks(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size){
        BooksService booksService = new BooksService(booksCatalogueRepository);
        try{
            List <BookDTO> books = booksService.getAllBooksInStock(page, size);
            return ResponseEntity.ok(books);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * This method is used to retrieve all the books that fit into the filter
     * @param author string author that is used by the bookService object to filter by author
     * @param priceRange string representation of the price range user wants to query used by bookService
     * @param category string representation of the category book field that the user wants filter by
     * @return A response entity that communicates the http status and the booksDTO that will be in the body
     *  */
    @GetMapping("/books/filtered")
    public ResponseEntity<List<BookDTO>> showFilteredCatalogue(@RequestParam(name= "author",required = false)String author, @RequestParam(name= "priceRange",required = false)String priceRange,
                                        @RequestParam(name = "category",required = false) String category, @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size,HttpSession session){
        BooksService booksService = new BooksService(booksCatalogueRepository);
        UserService userService = new UserService(userRepository);
        List<BookDTO> books =new ArrayList<>();
        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));

        books = booksService.getFilteredBooks(author,category,priceRange,page,size);
        return ResponseEntity.ok(books);
    }
    /**
     * This method is used to retrieve all the books that fit into the filter
     * @param query string author that is used by the bookService object to filter by author
     * @return A response entity that communicates the http status and the booksDTO that will be in the body
     *  */
    @GetMapping("/books/search")
    public ResponseEntity<List<BookDTO>>  showSearchResults(@RequestParam("query")String query,HttpSession session){
        UserService userService = new UserService(userRepository);
        BooksService booksService = new BooksService(booksCatalogueRepository);

        int currentUserId = userService.findUser((String)session.getAttribute("userEmail"));
        List<BookDTO> books = booksService.getSearchResultsBooks(query);

        return ResponseEntity.ok(books);
    }
}
