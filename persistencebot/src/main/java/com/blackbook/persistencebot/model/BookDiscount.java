package com.blackbook.persistencebot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by tymek on 23.08.17.
 */
@EqualsAndHashCode(exclude = {"id", "book", "bookstore"})
@Setter
@Entity
@Table(name = "book_discounts")
public class BookDiscount {

    @Getter
    @Id
    @GeneratedValue
    private long id;

    @Getter
    @Column(name = "book_discount_details")
    private String bookDiscountDetails;

    @Getter
    @Column(name = "price")
    private double price;

    @Getter
    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Book book;

    @Getter
    @JoinColumn(name = "bookstore_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Bookstore bookstore;
}
