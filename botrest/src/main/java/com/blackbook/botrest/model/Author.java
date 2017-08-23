package com.blackbook.botrest.model;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tymek on 23.08.17.
 */

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Generated
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "books_authors", joinColumns = {
            @JoinColumn(name = "author_id")}, inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Book> books;

}
