package com.example.demo.Services;

import com.example.demo.Entities.Book;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.exceptions.InvalidRangeException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BooksService {

    private final BooksCatalogueRepository booksCatalogueRepository;

    public BooksService(BooksCatalogueRepository booksCatalogueRepository) {
        this.booksCatalogueRepository = booksCatalogueRepository;
    }

    @Transactional
    public List<Book> getAllBooks() {
        return booksCatalogueRepository.findAll();
    }

    @Transactional
    public List<Book> getAllBooksInStock() {
        return booksCatalogueRepository.findByStockNot(0);
    }

    public List<Book> getFilteredBooks(String author, String category, String priceRange) {
        List<Book> books=new ArrayList<>();
        double min;
        double max;
        //all queried fields are empty default to return all
        if(priceRange.isEmpty() && author.isEmpty() && category.isEmpty()){
            return this.getAllBooksInStock();
        }
        if (priceRange.isEmpty()){
            //Author is not empty but category is empty and price range is empty
            if(!author.isEmpty() && category.isEmpty()){
                return booksCatalogueRepository.findByAuthorAndStockGreaterThan(author,0);
            }else if (author.isEmpty()){
                //Author and price are the only empty fields
                return booksCatalogueRepository.findByCategoryAndStockGreaterThan(category,0);
            }else
            //not only price range is empty
                return booksCatalogueRepository. findByAuthorAndCategoryAndStockGreaterThan(author,category,0);
        }else{
            //price range is not empty so we need to parse the string value to make it of value range
            try {
                double[] rangeParsed = parseRange(priceRange);
                min = rangeParsed[0];
                max = rangeParsed[1];

            } catch (InvalidRangeException e){
                throw e;
            }
            if(!author.isEmpty() && category.isEmpty()){
                //only category is empty
                return booksCatalogueRepository.findByAuthorAndPriceBetweenAndStockGreaterThan(author,min,max,0);
            }else if(author.isEmpty() && !category.isEmpty()){
                //only author is empty
                return booksCatalogueRepository.findByCategoryAndPriceBetweenAndStockGreaterThan(category, min, max,0);
            }
            else if(author.isEmpty()){
                //only price range is available
                return booksCatalogueRepository.findByPriceBetweenAndStockGreaterThan(min,max,0);
            }else
               return booksCatalogueRepository.findByAuthorAndCategoryAndPriceBetweenAndStockGreaterThan(
                        author, category, min, max, 0);
        }

    }

    public double[] parseRange(String priceRange) {
        double[] priceRangeDouble = new double[2];
        if (priceRange.equals("500+")) {
            priceRangeDouble[0] = 500;
            priceRangeDouble[1] = Double.POSITIVE_INFINITY;

        } else {
            try {
                String[] priceRangeStr = priceRange.split("-");
                priceRangeDouble[0] = Double.parseDouble(priceRangeStr[0]);
                priceRangeDouble[1] = Double.parseDouble(priceRangeStr[1]);

            } catch (Exception e) {
                throw new InvalidRangeException("Invalid Price Range was entered!");
            }
        }
        return priceRangeDouble;
    }

    public List<Book> getSearchResultsBooks(String query){
        return  booksCatalogueRepository.findBooksByQuery(query);

    }

}
