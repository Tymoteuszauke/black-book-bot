package com.blackbook.persistencebot.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by tymek on 23.08.17.
 */
@Data
@Entity
@Table(name = "book_discounts")
public class BookDiscount {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "book_discount_details")
    private String bookDiscountDetails;

    @Column(name = "price")
    private double price;

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Book book;

    @JoinColumn(name = "bookstore_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Bookstore bookstore;
}
