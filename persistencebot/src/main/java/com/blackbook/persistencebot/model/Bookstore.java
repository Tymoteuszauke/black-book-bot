package com.blackbook.persistencebot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tymek on 23.08.17.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "bookDiscounts"})
@Entity
@Table(name = "bookstores")
@ToString(exclude = {"bookDiscounts"})
public class Bookstore {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @OneToMany(mappedBy = "bookstore")
    List<BookDiscount> bookDiscounts;
}
