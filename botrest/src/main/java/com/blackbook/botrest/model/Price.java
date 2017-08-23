package com.blackbook.botrest.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by tymek on 23.08.17.
 */
@Data
@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "promotion_details")
    private String promotionDetails;

    @Column(name = "price")
    private double price;

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Book book;

    @JoinColumn(name = "bookstore_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Bookstore bookstore;
}
