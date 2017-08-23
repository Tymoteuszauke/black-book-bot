package com.blackbook.botrest.model;

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

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    @ManyToMany(mappedBy = "book")
    private List<Price> prices;
}
