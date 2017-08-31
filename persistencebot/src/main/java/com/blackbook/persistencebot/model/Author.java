package com.blackbook.persistencebot.model;

import lombok.Data;

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
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
