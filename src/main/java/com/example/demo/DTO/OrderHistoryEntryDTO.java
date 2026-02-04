package com.example.demo.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * Class Represents the DTO that contains details of the user's order history
 *
 * */
public class OrderHistoryEntryDTO implements Serializable {

    @Getter
    @Column(nullable=false)
    private int transactionId;

    @Getter
    @Setter
    private int customerId;

    @Getter
    @Setter
    private int bookIsbn;

    @Getter
    @Setter
    private String bookTitle;

    @Getter
    @Setter
    private String bookAuthor;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private double pricePerBook;
    @Getter
    @Setter
    private double total;

    @Getter
    @Setter
    private LocalDateTime transactionDate;
    public OrderHistoryEntryDTO(int transactionId,int customerId,int bookIsbn,
                                String bookTitle, String bookAuthor, int quantity, double price, double total,
                                LocalDateTime date){
        this.transactionId = transactionId;
        this.customerId  = customerId;
        this.bookIsbn = bookIsbn;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.quantity = quantity;
        this.pricePerBook =price;
        this.total = total;
        this.transactionDate =date;
    }
}
