package com.blackbook.botrest.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;
}
