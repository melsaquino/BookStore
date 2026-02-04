package com.example.demo.Services;

import com.example.demo.DTO.OrderHistoryEntryDTO;
import com.example.demo.Entities.Book;
import com.example.demo.Entities.Order;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Service class that primarily works with getting the order history of the user
 * */
public class OrderHistoryService {
    private final BooksCatalogueRepository booksCatalogueRepository;
    private final OrdersRepository ordersRepository;

    public OrderHistoryService(OrdersRepository ordersRepository,BooksCatalogueRepository booksCatalogueRepository){
        this.ordersRepository=ordersRepository;
        this.booksCatalogueRepository =booksCatalogueRepository;

    }
    /**
     * Method gets all the books the user have ordered/checked out
     * @param userId the user's unique Id used to ensure that the Books were ordered by only this specific user
     * @return returns a list of OrderHistoryDTO to be shown to the user
     * */
    public  List<OrderHistoryEntryDTO>getBooksOrderedByCustomerId(int userId){
        List<Order> userOrders =this.ordersRepository.getOrdersByCustomerId(userId);
        List<OrderHistoryEntryDTO> customerOrderedBooks =new ArrayList<>();
        for(Order order:userOrders){
            Book book = booksCatalogueRepository.findByIsbn(order.getBookIsbn());
            customerOrderedBooks.add(new OrderHistoryEntryDTO(order.getTransactionId(), order.getCustomerId(), order.getBookIsbn(),
                    book.getTitle(),book.getAuthor(),order.getQuantity(), book.getPrice(), order.getTotal(),order.getTransactionDate()));
        }
        customerOrderedBooks.sort(Comparator.comparing(OrderHistoryEntryDTO::getTransactionDate).reversed());
        return customerOrderedBooks;
    }
}
