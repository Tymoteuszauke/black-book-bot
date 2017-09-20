package com.blackbook.persistencebot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
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


    @Column(name = "authors")
    private String authors;

    @Column(name = "publisher")
    private  String publisher;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "book_page_url")
    private String bookPageUrl;

    @ManyToMany
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookDiscount> bookDiscounts;
}
