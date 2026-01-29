package com.example.demo.Repositories;

import com.example.demo.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BooksCatalogueRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
    //ArrayList<Book> findAllBooks();
    List<Book>getAllByAuthor(String author);
    List<Book>findByDescriptionContaining(String description);
    List<Book>findByPrice(double price);


    //List<Book> findByAuthor(String );
}
