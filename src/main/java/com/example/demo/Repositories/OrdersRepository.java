package com.example.demo.Repositories;
import org.springframework.data.domain.Pageable;

import com.example.demo.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByCustomerId(int userId);
    List<Order> findOrdersByTransactionDate(LocalDateTime date);
    List<Order> findOrdersByTransactionDate(LocalDateTime startDate,LocalDateTime endDate);

    List<Order> getOrdersByCustomerId(int userId, Pageable pageable);
}
