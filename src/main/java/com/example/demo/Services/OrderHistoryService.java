package com.example.demo.Services;

import com.example.demo.Entities.Book;
import com.example.demo.Entities.Order;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.ShoppingCartRepository;
import com.example.demo.Repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

public class OrderHistoryService {
    private final BooksCatalogueRepository booksCatalogueRepository;
    private final OrdersRepository ordersRepository;

    public OrderHistoryService(OrdersRepository ordersRepository,BooksCatalogueRepository booksCatalogueRepository){
        this.ordersRepository=ordersRepository;
        this.booksCatalogueRepository =booksCatalogueRepository;

    }

    public  Map<Order,Book>getBooksOrderedByCustomerId(int userId){
        List<Order> userOrders =this.ordersRepository.getOrdersByCustomerId(userId);
        userOrders.sort(Comparator.comparing(Order::getTransactionDate).reversed());
        Map<Order,Book> customerBooks =new LinkedHashMap<>();

        for(Order order:userOrders){
            Book book = booksCatalogueRepository.findByIsbn(order.getBookIsbn());
            customerBooks.put(order,book);
        }

        return customerBooks;
    }
}
