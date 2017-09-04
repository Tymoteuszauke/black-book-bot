package com.blackbook.persistencebot.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "genre")
    private String genre;

    @Column(name = "authors")
    private String authors;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "book_page_url")
    private String bookPageUrl;

    @ManyToMany(mappedBy = "book")
    private List<BookDiscount> bookDiscounts;
}
