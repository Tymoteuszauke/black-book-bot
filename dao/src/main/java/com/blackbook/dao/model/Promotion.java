package com.blackbook.dao.model;

import lombok.Data;

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
@Data
@Entity
@Table(name = "promotions")
public class Promotion {

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
