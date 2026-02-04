package com.example.demo.Repositories;

import com.example.demo.Entities.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksCatalogueRepository extends JpaRepository<Book, String> {
    Book findByIsbn(int isbn);
    List<Book>findByPriceBetweenAndStockGreaterThan(double min,double max,int stock);
    List<Book>findByAuthorAndCategoryAndPriceBetweenAndStockGreaterThan(String author,String category,double min,double max,int stock);
    List<Book>findByCategoryAndStockGreaterThan(String Category,int stock);
    List<Book>findByStockGreaterThan(int val);
    List<Book>findByStockGreaterThan(int val, Pageable pageable);

    List<Book> findByAuthorAndCategoryAndStockGreaterThan(String author, String category, int stock);
    List<Book>findByAuthorAndPriceBetweenAndStockGreaterThan(String author,double min, double max,int stock );

    List<Book> findByAuthorAndStockGreaterThan(String author, int stock);

    List<Book> findByCategoryAndPriceBetweenAndStockGreaterThan(String category, double min, double max, int stock);
    @Query("SELECT e FROM Book e WHERE " +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.author) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> findBooksByQuery(@Param("query") String query);

}
