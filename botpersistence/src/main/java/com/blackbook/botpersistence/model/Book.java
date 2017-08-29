package com.blackbook.botpersistence.model;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "books_authors",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")}
    )
    private List<Author> authors;

    @ManyToMany(mappedBy = "book")
    private List<Promotion> promotions;
}
