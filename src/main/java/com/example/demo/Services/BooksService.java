package com.example.demo.Services;

import com.example.demo.Models.Book;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.UserRepository;
import jakarta.transaction.Transactional;

import java.util.List;

public class BooksService {
    private BooksCatalogueRepository booksCatalogueRepository;
    public BooksService(BooksCatalogueRepository booksCatalogueRepository){
        this.booksCatalogueRepository=booksCatalogueRepository;
    }
    @Transactional
    public List<Book> getAllBooks(){
        return booksCatalogueRepository.findAll();
    }
}
