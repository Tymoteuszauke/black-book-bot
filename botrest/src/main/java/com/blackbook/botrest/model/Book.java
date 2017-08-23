package com.blackbook.botrest.model;

import lombok.Data;

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

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    @ManyToMany(mappedBy = "book")
    private List<Promotion> promotions;
}
