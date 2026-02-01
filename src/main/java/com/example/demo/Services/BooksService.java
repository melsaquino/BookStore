package com.example.demo.Services;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Entities.Book;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Exceptions.InvalidRangeException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BooksService {

    private final BooksCatalogueRepository booksCatalogueRepository;

    public BooksService(BooksCatalogueRepository booksCatalogueRepository) {
        this.booksCatalogueRepository = booksCatalogueRepository;
    }

    @Transactional
    public List<BookDTO> getAllBooksInStock() {
        List<Book> booksInStock= booksCatalogueRepository.findByStockNot(0);
        List<BookDTO> booksInStockDto = booksInStock.stream()
                .map(book ->
                new BookDTO(book.getIsbn(), book.getTitle(),book.getAuthor(),book.getPrice(),
                        book.getDescription(),book.getCategory(), book.getStock())
                ).collect(Collectors.toList());


        return booksInStockDto;
    }

    public List<BookDTO> getFilteredBooks(String author, String category, String priceRange) {
        List<Book> books = new ArrayList<>();
        double min;
        double max;

        boolean hasAuthor = author != null && !author.isEmpty();
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasPrice = priceRange != null && !priceRange.isEmpty();
        //all queried fields are empty default to return all
        if(!hasAuthor && !hasCategory && !hasPrice){
            return this.getAllBooksInStock();
        }
        if (!hasPrice){
            //Author is not empty but category is empty and price range is empty
            if(hasAuthor && !hasCategory){
                books=booksCatalogueRepository.findByAuthorAndStockGreaterThan(author,0);

            }else if (hasCategory && !hasAuthor){
                //Author and price are empty fields
                books=booksCatalogueRepository.findByCategoryAndStockGreaterThan(category,0);
            }else
            //not only price range is empty
                books= booksCatalogueRepository. findByAuthorAndCategoryAndStockGreaterThan(author,category,0);
        }else {
            //price range is not empty so we need to parse the string value to make it of value range
            try {
                double[] rangeParsed = parseRange(priceRange);
                min = rangeParsed[0];
                max = rangeParsed[1];

            } catch (InvalidRangeException e) {
                throw e;
            }
            if (hasAuthor && !hasCategory) {
                //only category is empty
                books = booksCatalogueRepository.findByAuthorAndPriceBetweenAndStockGreaterThan(author, min, max, 0);
            } else if (!hasAuthor && hasCategory) {
                //only author is empty
                books = booksCatalogueRepository.findByCategoryAndPriceBetweenAndStockGreaterThan(category, min, max, 0);
            } else if (!hasAuthor && !hasCategory) {
                //only price range is available
                books = booksCatalogueRepository.findByPriceBetweenAndStockGreaterThan(min, max, 0);
            } else
                books = booksCatalogueRepository.findByAuthorAndCategoryAndPriceBetweenAndStockGreaterThan(
                        author, category, min, max, 0);
            }
            return books.stream()
                    .map(book ->
                            new BookDTO(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPrice(),
                                    book.getDescription(), book.getCategory(), book.getStock())
                    ).collect(Collectors.toList());

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
                if (priceRangeDouble[0]>priceRangeDouble[1])
                    throw new InvalidRangeException("Invalid Price Range was entered!");
            } catch (Exception e) {
                throw new InvalidRangeException("Invalid Price Range was entered!");
            }
        }
        return priceRangeDouble;
    }

    public List<BookDTO> getSearchResultsBooks(String query){
        List<Book> bookResults =booksCatalogueRepository.findBooksByQuery(query);
        return bookResults.stream()
                .map(book ->
                        new BookDTO(book.getIsbn(),book.getTitle(),book.getAuthor(),book.getPrice(),
                                book.getDescription(),book.getCategory(), book.getStock())
                ).collect(Collectors.toList());

    }

}
