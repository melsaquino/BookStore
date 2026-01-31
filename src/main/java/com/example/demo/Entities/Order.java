package com.example.demo.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.*;
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(nullable=false)
    private int transactionId;

    @Getter
    @Setter
    @Column(nullable=false)
    private int customerId;


    @Getter
    @Setter
    @Column(nullable=false)
    private int bookIsbn;

    @Getter
    @Setter
    @Column(nullable = false)
    private int quantity;

    @Getter
    @Setter
    @Column(nullable=false)
    private double total;

    @Getter
    @Setter
    @Column(nullable=false)
    private LocalDateTime transactionDate;

    public Order(){
        transactionDate=LocalDateTime.now();
    }



}
