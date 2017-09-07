package com.blackbook.persistencebot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "authors", "coverUrl", "bookPageUrl", "bookDiscounts"})
@Entity
@Table(name = "books")
@ToString(exclude = {"bookDiscounts"})
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
