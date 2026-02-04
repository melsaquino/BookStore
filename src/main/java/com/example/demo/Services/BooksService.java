package com.example.demo.Services;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Entities.Book;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.*;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service class that handles a lot of the processes involved with the home page
 * */
public class BooksService {
    /**
     * The JPARepository interface that will directly query in the books table of the database
     * */
    private final BooksCatalogueRepository booksCatalogueRepository;
    private final UserRepository userRepository;

    public BooksService(BooksCatalogueRepository booksCatalogueRepository, UserRepository userRepository) {
        this.booksCatalogueRepository = booksCatalogueRepository;
        this.userRepository =userRepository;
    }
    public BooksService(BooksCatalogueRepository booksCatalogueRepository) {
        this.booksCatalogueRepository = booksCatalogueRepository;
        userRepository =null;
    }
    /**
     * This method queries the booksCatalogueRepository to get all books in the database with stock greater than 0
     * @return a list of BookDTO representing books in stock in the books store.
     * The BookDTO contains the information that the user and the front-end may need
     * */
    @Transactional
    public List<BookDTO> getAllBooksInStock(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Book> booksInStock= booksCatalogueRepository.findByStockGreaterThan(0,pageable);
        List<BookDTO> booksInStockDto = booksInStock.stream()
                .map(book ->
                new BookDTO(book.getIsbn(), book.getTitle(),book.getAuthor(),book.getPrice(),
                        book.getDescription(),book.getCategory(), book.getStock())
                ).collect(Collectors.toList());
        
        return booksInStockDto;
    }

    /**
     * This method filters out all the books that are in stocks based on what the user wants
     * @param author represents the book author that the user wants
     * @param category represents what book category the user wants
     * @param priceRange represents the price range of the book that the user wants
     * @return List of BookDTO where the fields'values strictly conform to the parameters
     * */
    public List<BookDTO> getFilteredBooks(String author, String category, String priceRange,int page,int size) {
        List<Book> books = new ArrayList<>();
        double min;
        double max;

        boolean hasAuthor = author != null && !author.isEmpty();
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasPrice = priceRange != null && !priceRange.isEmpty();
        //all queried fields are empty default to return all
        if(!hasAuthor && !hasCategory && !hasPrice){
            return this.getAllBooksInStock(page,size);
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
    /**
     * Method parses the price range that was entered into valid values
     * @param priceRange the string the user entered that should represent the price range to be filtered
     * @return returns a double array that contains the minimum and maximum values user wants to filter
     * */
    private double[] parseRange(String priceRange) {
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
    /**
     * Service method that handles the search functionalities
     * @param query the search query that the user entered in the search bar
     * @return a list of books that are similar to either the author, title ot description of the query
     * */
    public List<BookDTO> getSearchResultsBooks(String query){
        List<Book> bookResults =booksCatalogueRepository.findBooksByQuery(query);
        return bookResults.stream()
                .map(book ->
                        new BookDTO(book.getIsbn(),book.getTitle(),book.getAuthor(),book.getPrice(),
                                book.getDescription(),book.getCategory(), book.getStock())
                ).collect(Collectors.toList());
    }
    /**
     * Method used to add books to the books table of the database
     * @param admin_id ID number of the admin adding the book
     * @param title title of the book
     * @param author author of the book
     * @param category category of the book
     * @param price the price of the book
     * @param description the description of the book
     * @param stock the stock of the book
     * */
    public void addBooks(int admin_id, String title, String author,String category, String price,
                         String description, String stock) throws Exception {
        if (!userRepository.findById(admin_id).getRole().equals("ADMIN"))
            throw new InvalidAccessException("Invalid access");
        if(!category.equalsIgnoreCase("fiction") && !category.equalsIgnoreCase("non-fiction"))
            throw new Exception("Invalid category.");
        double priceDouble;
        int stockInt;
        try {
            priceDouble= Double.parseDouble(price);
        }catch(NumberFormatException e){
            throw new Exception ("Invalid price entered");
        }
        try{
            stockInt = Integer.parseInt(stock);
        }catch(NumberFormatException e){
            throw new Exception ("Invalid stock entered");
        }
        if(priceDouble<=0)
            throw new Exception("Invalid price value");
        if(stockInt<0){
            throw new Exception("Invalid stock value");
        }

            Book book= new Book(author,title);
            book.setCategory(category);
            book.setPrice(priceDouble);
            book.setDescription(description);
            book.setStock(stockInt);
            this.booksCatalogueRepository.save(book);

    }


}
