package com.blackbook.persistencebot.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tymek on 23.08.17.
 */
@Data
@Entity
@Table(name = "bookstores")
public class Bookstore {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @OneToMany(mappedBy = "bookstore")
    List<Promotion> promotions;
}
