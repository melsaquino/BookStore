package com.example.demo.Services;

import com.example.demo.Entities.Book;
import com.example.demo.Repositories.BooksCatalogueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BooksService {

    private final BooksCatalogueRepository booksCatalogueRepository;

    public BooksService(BooksCatalogueRepository booksCatalogueRepository){
        this.booksCatalogueRepository=booksCatalogueRepository;
    }
    @Transactional
    public List<Book> getAllBooks(){
        return booksCatalogueRepository.findAll();
    }

    @Transactional
    public List<Book> getAllBooksInStock(){
        return booksCatalogueRepository.findByStockNot(0);
    }


}
